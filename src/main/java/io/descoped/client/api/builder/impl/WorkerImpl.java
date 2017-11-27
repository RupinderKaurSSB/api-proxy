package io.descoped.client.api.builder.impl;

import io.descoped.client.api.builder.Builder;
import io.descoped.client.api.builder.Worker;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class WorkerImpl implements Worker {

    private Builder parent;
    private String id;
    private Class<? extends OperationHandler> operation;
    private Class<? extends OutcomeHandler> outcome;

    public WorkerImpl(Builder parent, String id) {
        this.parent = parent;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public Worker operation(Class<? extends OperationHandler> operation) {
        this.operation = operation;
        return this;
    }

    public Class<? extends OperationHandler> getOperation() {
        return operation;
    }

    @Override
    public Worker outcome(Class<? extends OutcomeHandler> outcome) {
        this.outcome = outcome;
        return this;
    }

    public Class<? extends OutcomeHandler> getOutcome() {
        return outcome;
    }

    @Override
    public Builder done() {
        return parent;
    }
}
