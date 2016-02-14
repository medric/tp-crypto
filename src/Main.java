import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
		// Q.1
		/*BigInteger[] ns = { new BigInteger("48"), new BigInteger("53") };
		
		boolean displayed = true;
		for (BigInteger n : ns) {
			for (BigInteger x = BigInteger.ZERO; x.compareTo(n) == -1; x = x
					.add(BigInteger.ONE)) {
				displayed = false;
				for (BigInteger y = BigInteger.ZERO; y.compareTo(n) == -1; y = y
						.add(BigInteger.ONE)) {
					if (x.mod(n).compareTo(y.multiply(y).mod(n)) == 0) {
						if (!displayed) {
							System.out.println(x + " : ");
							displayed = true;
						}
						System.out.println("\t => " + y);
					}
				}
			}
			System.out.println("-------------------------------");
		}*/
		
		// Q.3
		/*BigInteger i = new BigInteger("54");
		
		isPrime(i);*/
		
		// QAndA
		try {
			QAndA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void isPrime(BigInteger i) {
		boolean isPrime = true;
		
		for(BigInteger x = BigInteger.ZERO; x.compareTo(i) == -1; x.add(BigInteger.ONE)) {
			System.out.println("current " + x.toString());
			if(i.mod(x).equals(0)) {
				isPrime = false;
			}
		}
		
		if(isPrime) {
			System.out.println(i + " is prime");
		} else {
			System.out.println(i + " is not prime");
		}
	}
	
	private final static Random rand = new Random();

    private static BigInteger getRandomFermatBase(BigInteger n)
    {
        // Rejection method: ask for a random integer but reject it if it isn't
        // in the acceptable set.

        while (true)
        {
            final BigInteger a = new BigInteger (n.bitLength(), rand);
            // must have 1 <= a < n
            if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(n) < 0)
            {
                return a;
            }
        }
    }

    private static boolean checkPrime(BigInteger n, int maxIterations)
    {
	    if (n.equals(BigInteger.ONE))
	        return false;
	
	    for (int i = 0; i < maxIterations; i++)
	    {
	        BigInteger a = getRandomFermatBase(n);
	        a = a.modPow(n.subtract(BigInteger.ONE), n);
	
	        if (!a.equals(BigInteger.ONE))
	            return false;
	    }
	
	    return true;
    }
    
    private static BigInteger getRandomPrimeNumber(int length) {
        while (true)
        {
            final BigInteger a = new BigInteger (length, 0, rand);
            // must have 1 <= a < n
            if (checkPrime(a, 1000))
            {
                return a;
            }
        }
    }
    
    // Q.6
    public static void computePq() {
    	BigInteger p = getRandomPrimeNumber(512);
    	BigInteger q = getRandomPrimeNumber(512);
    	
    	BigInteger n = p.multiply(q);
    	BigInteger o = p.subtract(BigInteger.ONE).multiply(p.subtract(BigInteger.ONE)); 
    }
    
    // Q.7
    public static BigInteger primeWithO(BigInteger o) {
    	int it = 0;
    	while(++it < 2000) {
    		BigInteger e = getRandomPrimeNumber(16);
        	
    		if(e.gcd(o).equals(BigInteger.ONE)) {
    			return e;
    		}
    		
    	}
    	
    	return null;
    }
    
    public static void modularInverse(BigInteger o) {
    	BigInteger e = getRandomPrimeNumber(16);
    	BigInteger d = e.modInverse(o);
    }
    
    // PROJET 
    
    public static void QAndA() throws Exception {
    	Paillier paillier = new Paillier(1024);
    	ArrayList<String> answers = new ArrayList<String>();
    	
    	// List of answers
    	answers.add("solution");
    	answers.add("password");
    	answers.add("validation");
    	answers.add("secret");
    	answers.add("admin");
    	
    	// Set answers
    	Provider p = new Provider(answers, paillier);
    	// Choose question n°0
    	Enquire e = new Enquire(4, paillier);
    	
    	// Decode reponse
    	ArrayList<BigInteger> ciphers = e.send(p.getCeiling()); // 	enquire
    	ArrayList<BigInteger> responses = p.respond(ciphers); // provider
    	BigInteger clearedAnswer = e.decrypt(responses);
    	
    	// Retrieve the answer
    	System.out.println("Response to question " + e.getChosenQuestionNumber() + " is : " + bigIntegerToText(clearedAnswer));
    }
    
    /**
     * convert to big integer
     * @param m
     * @return
     */
    public static BigInteger textToBigInteger(String m) {
    	BigInteger bigInt = new BigInteger(m.getBytes());
    	return bigInt;
    }
    
    /**
     * convert back to text
     * @param bigInt
     * @return
     */
    public static String bigIntegerToText(BigInteger bigInt) {
    	String textBack = new String(bigInt.toByteArray());
    	return textBack;
    }
}