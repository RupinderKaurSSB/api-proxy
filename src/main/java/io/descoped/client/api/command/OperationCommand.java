package io.descoped.client.api.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class OperationCommand extends HystrixCommand<OutcomeHandler> {

    private final OperationHandler operationHandler;
//    private final OutcomeHandler outcomeHandler;

    public OperationCommand(OperationHandler operationHandler) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OperationCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(300000))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(100)
                                .withQueueSizeRejectionThreshold(100)
                                .withCoreSize(4)));

        this.operationHandler = operationHandler;

        HystrixRequestContext.initializeContext();
    }

    @Override
    protected OutcomeHandler run() throws Exception {
        boolean ok = operationHandler.execute();
        return operationHandler.getOutcomeHandler();
    }
}
