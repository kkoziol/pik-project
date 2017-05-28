package com.project.pik.EbayApi.daemon;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.project.pik.EbayApi.daos.Offer;

public class SearchDaemonFutureResult implements Future<List<Offer>>{
	private final CountDownLatch latch = new CountDownLatch(1);
	private List<Offer> result;
	
	@Override
	public boolean isDone() {
		return latch.getCount() == 0;
	}
	
	@Override
	public boolean isCancelled() {
		return false;
	}
	
	@Override
	public List<Offer> get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		if (latch.await(timeout, unit)) {
            return result;
        } else {
            throw new TimeoutException();
        }
	}
	
	@Override
	public List<Offer> get() throws InterruptedException, ExecutionException {
		latch.await();
		return result;
	}
	
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}
	
    void putResult(List<Offer> result) {
    	this.result = result;
        latch.countDown();
    }
}
