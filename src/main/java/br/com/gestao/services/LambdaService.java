package br.com.gestao.services;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LambdaService {

    @Autowired
    private AWSLambda awsLambda;

    public void invocarLambda(String payload) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName("nome-da-funcao-lambda")
                .withPayload(payload);

        InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
        // Manipular o resultado, se necessário
    }
}
