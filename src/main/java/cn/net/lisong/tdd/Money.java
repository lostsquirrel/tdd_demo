package cn.net.lisong.tdd;

public class Money implements Expression {

	protected int amount;

	protected String currency;

	public Money(int amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	static Money dollar(int amount) {
		return new Money(amount, "USD");
	}

	static Money franc(int amount) {
		return new Money(amount, "CHF");
	}

	public Expression plus(Expression addend) {
		return new Sum(this, addend);
	}

	public Money reduce(Bank bank, String to) {
		int rate = bank.rate(currency, to);
		return new Money(amount / rate, to);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		Money otherx = (Money) other;
		return this.amount == otherx.amount
				&& this.currency().equals(otherx.currency());
	}

	public Expression times(int multiplier) {
		return new Money(amount * multiplier, currency);
	}

	@Override
	public String toString() {
		return amount + " " + currency;
	}

	public int getAmount() {
		return amount;
	}

	public String currency() {
		return currency;
	}

}
