#!/usr/bin/env bash
set -euo pipefail
IFS=$'\n\t'

# Decide qual comando Maven usar: prefira o wrapper ./mvnw quando existir
if [[ -x "./mvnw" ]]; then
  mvncmd="./mvnw"
elif [[ -f "./mvnw" ]]; then
  # existe mas não é executável -> execute via bash
  mvncmd="bash ./mvnw"
else
  # fallback para mvn no PATH
  if command -v mvn >/dev/null 2>&1; then
    mvncmd="mvn"
  else
    echo "Erro: nem ./mvnw nem mvn foram encontrados. Instale o Maven ou inclua o wrapper (./mvnw) no projeto." >&2
    exit 1
  fi
fi

# Obtém artifactId e version do pom (silencioso)
artifact=$($mvncmd help:evaluate -Dexpression=project.artifactId -q -DforceStdout || true)
version=$($mvncmd help:evaluate -Dexpression=project.version -q -DforceStdout || true)

artifact=$(echo "$artifact" | tr -d '\r' | xargs)
version=$(echo "$version" | tr -d '\r' | xargs)

if [[ -z "$artifact" || -z "$version" ]]; then
  echo "Erro: não foi possível ler artifactId/version do pom.xml (artifact='$artifact' version='$version')." >&2
  exit 1
fi

# Separa qualifier (ex.: -SNAPSHOT) do base version
if [[ "$version" == *"-"* ]]; then
  qualifier="${version#*-}"
  base_version="${version%%-*}"
else
  qualifier=""
  base_version="$version"
fi

# Incrementa a última parte numérica da versão (ex.: 1.2.3 -> 1.2.4)
IFS='.' read -ra parts <<< "$base_version"
last_index=$(( ${#parts[@]} - 1 ))
last_part="${parts[$last_index]}"
if [[ "$last_part" =~ ^[0-9]+$ ]]; then
  parts[$last_index]=$(( last_part + 1 ))
else
  # se não for puramente numérico, anexa '1'
  parts[$last_index]="${last_part}1"
fi

next_base_version=$(IFS=. ; echo "${parts[*]}")
if [[ -n "$qualifier" ]]; then
  next_version="${next_base_version}-${qualifier}"
else
  next_version="$next_base_version"
fi

# Verifica se Docker está disponível
if ! command -v docker >/dev/null 2>&1; then
  echo "Erro: docker não encontrado no PATH. Instale o Docker para construir a imagem." >&2
  exit 1
fi

# Verifica existência do Dockerfile
if [[ ! -f "Dockerfile" ]]; then
  echo "Erro: Dockerfile não encontrado no diretório atual." >&2
  exit 1
fi

# Build da imagem Docker com tag incremental
image_tag="${artifact}:${next_version}"

echo "Construindo imagem Docker: ${image_tag} (usando ${mvncmd})"
docker build -t "${image_tag}" -f Dockerfile .

# Saída com nome da imagem gerada
echo "Imagem gerada: ${image_tag}"
