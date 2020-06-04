package com.acme.mytrader.execution;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TradeExecutionService implements ExecutionService {

  private final int id;

  @Override
  public void buy(String security, double price, int volume) {
    System.out.printf("\n BUY Trade executed for %s @ £ %.2f for %d number of securities", security,
        price, volume);
  }

  @Override
  public void sell(String security, double price, int volume) {
    throw new UnsupportedOperationException("Out of scope for this inteview-excercise");
  }
}
