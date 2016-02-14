import java.math.BigInteger;
import java.util.ArrayList;

public class Enquire {
	public Paillier paillier;
	public Integer chosenQuestionNumber;
	
	public Enquire(Integer chosenQuestionNumber, Paillier p) {
		try {
			this.paillier = p;
			
			this.setChosenQuestionNumber(chosenQuestionNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Integer getChosenQuestionNumber() {
		return chosenQuestionNumber;
	}

	public void setChosenQuestionNumber(Integer chosenQuestionNumber) {
		this.chosenQuestionNumber = chosenQuestionNumber;
	}
	
	public BigInteger encrypt(BigInteger I, BigInteger i) throws Exception {
		BigInteger M = I.multiply(this.paillier.encrypt(i.mod(this.paillier.getN())).mod(this.paillier.getNsquare())).modPow(this.paillier.randomZN(), this.paillier.getNsquare()); 
		return M;
	}
		
	public BigInteger decrypt(ArrayList<BigInteger> secrets) throws Exception {
		return this.paillier.decrypt(secrets.get(this.chosenQuestionNumber).mod(this.paillier.getNsquare()));
	}
	
	public ArrayList<BigInteger> send(Integer ceiling) throws Exception {
		ArrayList<BigInteger> ciphers = new ArrayList<BigInteger>();
		BigInteger I = BigInteger.valueOf(this.chosenQuestionNumber.intValue());
		I = this.paillier.encrypt(I);
		for(Integer i = 0; i < ceiling; i++) {
			Integer _i = -i;
			BigInteger _I = BigInteger.valueOf(_i.intValue());
			ciphers.add(this.encrypt(I, _I));
		}
		return ciphers;
	}
}
