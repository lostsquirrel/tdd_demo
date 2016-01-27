package cn.net.lisong.tdd;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	public void testPlusSameCurrencyReturnsMoney() {
		Expression sum = Money.dollar(1).plus(Money.dollar(1));
		Assert.assertTrue(sum instanceof Money);
	}

	@Test
	public void testSumTimes() {
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
		Money result = bank.reduce(sum, "USD");
		Assert.assertEquals(Money.dollar(20), result);
	}

	@Test
	public void testSumPlusMoney() {
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
		Money result = bank.reduce(sum, "USD");
		Assert.assertEquals(Money.dollar(15), result);
	}

	@Test
	public void testMixedAddition() {
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
		Assert.assertEquals(Money.dollar(10), result);
	}

	@Test
	public void testIdentityRate() {
		Assert.assertEquals(1, new Bank().rate("USD", "USD"));
	}

	@Test
	public void testReduceMoneyDifferentCurrency() {
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Money result = bank.reduce(Money.franc(2), "USD");
		Assert.assertEquals(Money.dollar(1), result);
	}

	@Test
	public void testReduceMoney() {
		Bank bank = new Bank();
		Money result = bank.reduce(Money.dollar(1), "USD");
		Assert.assertEquals(Money.dollar(1), result);
	}

	@Test
	public void testReduceSum() {
		Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
		Bank bank = new Bank();
		Money result = bank.reduce(sum, "USD");
		Assert.assertEquals(Money.dollar(7), result);
	}

	@Test
	public void testPlusReturnsSum() {
		Money five = Money.dollar(5);
		Expression result = five.plus(five);
		Sum sum = (Sum) result;
		Assert.assertEquals(five, sum.augend);
		Assert.assertEquals(five, sum.addend);
	}

	@Test
	public void testSimpleAddition() {
		Money five = Money.dollar(5);
		Expression sum = five.plus(five);
		Bank bank = new Bank();
		Money reduced = bank.reduce(sum, "USD");
		Assert.assertEquals(Money.dollar(10), reduced);

		Assert.assertNotEquals(Money.dollar(15), reduced);
	}

	@Test
	public void testMultiplication() {
		Money five = Money.dollar(5);
		Expression product = five.times(2);
		product = five.times(3);
		Assert.assertEquals(Money.dollar(15), product);
		Assert.assertNotEquals(Money.dollar(10), product);
	}

	@Test
	public void testEquality() {
		Assert.assertTrue(Money.dollar(5).equals(Money.dollar(5)));
		Assert.assertFalse(Money.dollar(5).equals(Money.dollar(6)));
		Assert.assertTrue(Money.franc(5).equals(Money.franc(5)));
		Assert.assertFalse(Money.franc(5).equals(Money.franc(6)));
		Assert.assertFalse(Money.franc(5).equals(Money.dollar(5)));

	}

	@Test
	public void testCurrency() {
		Assert.assertEquals("USD", Money.dollar(1).currency());
		Assert.assertEquals("CHF", Money.franc(1).currency());
	}

	@Test
	public void testDifferentClassEquality() {
		Assert.assertTrue(new Money(10, "CHF").equals(Money.franc(10)));
	}
}
