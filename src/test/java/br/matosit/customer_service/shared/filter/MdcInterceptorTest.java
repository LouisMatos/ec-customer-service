package br.matosit.customer_service.shared.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class MdcInterceptorTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private Object handler;

  @InjectMocks
  private MdcInterceptor mdcInterceptor;

  @BeforeEach
  void setUp() {
    MDC.clear();
  }

  @Test
  void testPreHandle_ShouldAddXReqIdToMDC() throws Exception {
    // Act
    boolean result = mdcInterceptor.preHandle(request, response, handler);

    // Assert
    assertTrue(result);
    assertNotNull(MDC.get("X-ReqId"));
  }

  @Test
  void testAfterCompletion_ShouldRemoveXReqIdFromMDC() throws Exception {
    // Arrange
    MDC.put("X-ReqId", "test-req-id");

    // Act
    mdcInterceptor.afterCompletion(request, response, handler, null);

    // Assert
    assertNull(MDC.get("X-ReqId"));
  }
}
