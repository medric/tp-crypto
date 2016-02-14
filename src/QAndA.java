import java.math.BigInteger;
import java.util.ArrayList;


public class QAndA {
	public Paillier p;
	private Integer ceiling;
	private ArrayList<BigInteger> answers;
	
	public QAndA() throws Exception {
		this.p = new Paillier(1024);
		this.answers = new ArrayList<BigInteger>();
	}

	public BigInteger encrypt(BigInteger I, BigInteger i) throws Exception {
		BigInteger M = I.multiply(this.p.encrypt(i.mod(this.p.getN())).mod(this.p.getNsquare())).modPow(this.p.randomZN(), this.p.getNsquare()); 
		return M;
	}
	
	public BigInteger decrypt(BigInteger S) throws Exception {
		return this.p.decrypt(S);
	}
	
	public void cipher(BigInteger m) throws Exception {
		//System.out.println(this.p.encrypt(m));
		this.answers.add(this.p.encrypt(m));
		this.ceiling = this.answers.size();
	}
	
	public ArrayList<BigInteger> send(Integer chosenQuestionNumber) throws Exception {
		ArrayList<BigInteger> ciphers = new ArrayList<BigInteger>();
		BigInteger I = BigInteger.valueOf(chosenQuestionNumber.intValue());
		I = this.p.encrypt(I);
		for(Integer i = 0; i < this.ceiling; i++) {
			Integer _i = -i;
			BigInteger _I = BigInteger.valueOf(_i.intValue());
			ciphers.add(this.encrypt(I, _I));
		}
		return ciphers;
	}

	public ArrayList<BigInteger> respond(ArrayList<BigInteger> ciphers) {
		ArrayList<BigInteger> secrets = new ArrayList<BigInteger>();
		for(Integer i = 0; i < this.ceiling; i++) {
			BigInteger secret = this.answers.get(i).multiply(ciphers.get(i));
			secrets.add(secret);
		}
		return secrets;
	}
	
	public BigInteger decryptSecret(ArrayList<BigInteger> secrets, Integer chosenQuestionNumber) throws Exception {
		return this.p.decrypt(secrets.get(chosenQuestionNumber).mod(this.p.getNsquare()));
	}
}
