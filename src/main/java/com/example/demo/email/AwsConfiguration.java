package com.example.demo.email;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AwsConfiguration {
	   public AWSStaticCredentialsProvider awsCredentials() {
	        BasicAWSCredentials credentials =
	                new BasicAWSCredentials("your-aws-access-key", "your-aws-access-secret");
	        return new AWSStaticCredentialsProvider(credentials);
	    }

	    @Bean
	    public AmazonSimpleEmailService getAmazonSimpleEmailService() {
	        return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(awsCredentials())
	                .withRegion("us-east-1").build();
	    }
}
