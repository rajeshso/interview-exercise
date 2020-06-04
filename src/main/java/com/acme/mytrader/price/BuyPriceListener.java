package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BuyPriceListener implements PriceListener {

  private final String security;
  private final double triggerLevel;
  private final int quantityToPurchase;
  private final ExecutionService executionService;

  private boolean tradeExecuted;

  @Override
  public void priceUpdate(String security, double price) {
    if (canBuy(security, price)) {
      executionService.buy(security, price, quantityToPurchase);
      tradeExecuted = true;
    }
  }

  private boolean canBuy(String security, double price) {
    return (!tradeExecuted) && this.security.equals(security) && (price < this.triggerLevel);
  }
}
