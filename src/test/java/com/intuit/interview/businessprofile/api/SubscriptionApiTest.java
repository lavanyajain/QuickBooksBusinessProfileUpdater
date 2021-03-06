package com.intuit.interview.businessprofile.api;

import com.intuit.interview.businessprofile.model.BatchValidationResponse;
import com.intuit.interview.businessprofile.model.BusinessProfile;
import com.intuit.interview.businessprofile.model.SubscriptionRequest;
import com.intuit.interview.businessprofile.model.SubscriptionStatus;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.intuit.interview.businessprofile.config.Configuration.SUCCESS_STATUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SubscriptionApiTest {
    public static final List<String> list = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add("accounts");
                add("timesheet");
            }});
    @InjectMocks
    private final SubscriptionApi subscriptionApi = spy(new SubscriptionApi());
    @InjectMocks
    private final SubscriptionRequest subscriptionRequest = spy(new SubscriptionRequest());
    @InjectMocks
    private final BusinessProfile businessProfile = spy(new BusinessProfile());
    @InjectMocks
    private final BatchValidationResponse batchValidationResponse = spy(new BatchValidationResponse());

    @Test
    public void validateSubscribe() {
        doReturn(list).when(subscriptionApi).getAllSubscribedProducts(any());
        doReturn(new ArrayList<String>()).when(subscriptionRequest).getProducts();
        doReturn(businessProfile).when(subscriptionRequest).getBusinessProfile();
        doReturn(batchValidationResponse).when(subscriptionApi).getBatchValidationResponse(subscriptionRequest);
        doReturn(list).when(subscriptionRequest).getProducts();
        doNothing().when(subscriptionApi).createUpdate(any());
        doReturn(list).when(subscriptionApi).checkValidationStatus(any());

        SubscriptionStatus subscriptionStatus = subscriptionApi.subscribe(subscriptionRequest);

        assertThat(subscriptionStatus.getStatus()).isEqualTo(SUCCESS_STATUS);
    }
}
