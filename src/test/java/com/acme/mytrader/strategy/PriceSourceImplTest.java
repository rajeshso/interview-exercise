package com.acme.mytrader.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSourceImpl;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class PriceSourceImplTest {


  @Test
  public void addPriceListener_shouldAddToAList() {
    PriceListener priceListener = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener);
    assertThat(priceSource.getPriceListeners()).hasSize(1);
  }

  @Test
  public void addPriceListenerOfTwoListeners_shouldAddToAList() {
    PriceListener priceListener1 = Mockito.mock(PriceListener.class);
    PriceListener priceListener2 = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener1);
    priceSource.addPriceListener(priceListener2);

    assertThat(priceSource.getPriceListeners()).hasSize(2);
  }

  @Test
  public void removePriceListenerOfOneListeners_shouldRemoveListener() {
    PriceListener priceListener1 = Mockito.mock(PriceListener.class);
    PriceListener priceListener2 = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener1);
    priceSource.addPriceListener(priceListener2);
    priceSource.removePriceListener(priceListener2);
    assertThat(priceSource.getPriceListeners()).hasSize(1);
  }

  @Test
  @SneakyThrows
  public void givenOneListener_priceSourceShouldInvokeTheListener_whenThreadStarted() {
    PriceListener priceListener = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource1 = new PriceSourceImpl();
    priceSource1.addPriceListener(priceListener);
    Thread thread = new Thread(priceSource1);
    thread.start();
    thread.join();
    verify(priceListener, times(10)).priceUpdate(anyString(), anyDouble());
  }

  @Test
  @SneakyThrows
  public void givenOneListener_priceSourceShouldInvokeTheListenerWithSecurityAndPrice_whenThreadStarted() {
    List<String> SECURITIES = Arrays.asList("NVDA", "IBM", "HP", "AMD", "AMD", "GOOGL");
    ArgumentCaptor<String> securityCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Double> priceCaptor = ArgumentCaptor.forClass(Double.class);
    PriceListener priceListener = Mockito.mock(PriceListener.class);
    PriceSourceImpl priceSource = new PriceSourceImpl();
    priceSource.addPriceListener(priceListener);
    Thread thread = new Thread(priceSource);
    thread.start();
    thread.join();
    verify(priceListener, times(10)).priceUpdate(securityCaptor.capture(), priceCaptor.capture());
    assertThat(securityCaptor.getValue()).as("Should contain at least one value from Securities ")
        .matches(s -> SECURITIES.stream().anyMatch(s::contains));
    assertThat(priceCaptor.getValue()).as("Should be a double value between 1.00 to 200.00")
        .isGreaterThan(1.00).isLessThanOrEqualTo(200.00);
  }

}
