package com.acme.mytrader.strategy;

import static java.util.Arrays.asList;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.execution.TradeExecutionService;
import com.acme.mytrader.price.BuyPriceListener;
import com.acme.mytrader.price.PriceSourceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
@AllArgsConstructor
@Getter
public class TradingStrategy {

  private final ExecutionService tradeExecutionService;
  private final PriceSourceImpl priceSource;

  public void autoBuy(List<SecurityDTO> request) throws InterruptedException {

    request.stream().map(
        r -> new BuyPriceListener(r.getSecurity(), r.getPriceThreshold(), r.getVolume(),
            tradeExecutionService, false)).forEach(priceSource::addPriceListener);
    Thread thread = new Thread(priceSource);
    thread.start();
    thread.join();
    request.stream().map(
        r -> new BuyPriceListener(r.getSecurity(), r.getPriceThreshold(), r.getVolume(),
            tradeExecutionService, false)).forEach(priceSource::removePriceListener);
  }

  //This is a demo test
  public static void main(String[] args) throws InterruptedException {
    TradingStrategy tradingStrategy = new TradingStrategy(new TradeExecutionService(1),
        new PriceSourceImpl());
    final SecurityDTO ibm = SecurityDTO.builder().security("IBM").priceThreshold(100.00).volume(12)
        .build();
    final SecurityDTO google = SecurityDTO.builder().security("GOOGL").priceThreshold(100.00)
        .volume(24)
        .build();
    tradingStrategy.autoBuy(asList(ibm, google));
  }


}

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
class SecurityDTO {

  private final String security;
  private final double priceThreshold;
  private final int volume;
}
