package io.descoped.client.api.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.descoped.client.api.builder.Operation;
import io.descoped.client.api.builder.Outcome;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class OperationCommand extends HystrixCommand<Operation<Outcome>> {

    private final Operation operation;
    private final Outcome outcome;

    protected OperationCommand(Operation operation, Outcome outcome) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OperationCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(300000))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(100)
                                .withQueueSizeRejectionThreshold(100)
                                .withCoreSize(4)));

        this.operation = operation;
        this.outcome = outcome;

        HystrixRequestContext.initializeContext();
    }

    @Override
    protected Operation<Outcome> run() throws Exception {
        // maybe ask for params too
        boolean ok = operation.execute();
        if (ok) {
//            outcome.handleResponse(operation);
        }

        return null;
    }
}
