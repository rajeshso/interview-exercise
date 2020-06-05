package com.acme.mytrader.price;

public interface PriceSource extends Runnable {

  void addPriceListener(PriceListener listener);

  void removePriceListener(PriceListener listener);
}
