package br.com.gestao.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

public class LambdaServiceTest {

    @Mock
    private AWSLambda awsLambda;

    @InjectMocks
    private LambdaService lambdaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInvocarLambda() {
        // Arrange
        String payload = "{\"key\":\"value\"}";
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setStatusCode(200);
        
        when(awsLambda.invoke(any(InvokeRequest.class))).thenReturn(invokeResult);

        // Act
        lambdaService.invocarLambda(payload);

        // Assert
        verify(awsLambda, times(1)).invoke(argThat(invokeRequest ->
            "nome-da-funcao-lambda".equals(invokeRequest.getFunctionName()) &&
            payload.equals(invokeRequest.getPayload())
        ));
    }
}