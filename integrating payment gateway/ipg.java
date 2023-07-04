import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;

import java.util.HashMap;
import java.util.Map;

public class PaymentGatewayIntegration {

    // Set your Stripe API key
    private static final String STRIPE_API_KEY = "YOUR_STRIPE_API_KEY";

    public static void main(String[] args) {
        // Initialize Stripe with your API key
        Stripe.apiKey = STRIPE_API_KEY;

        try {
            // Create a payment token
            Token token = createToken();

            // Make a charge using the token
            String chargeId = createCharge(token);

            System.out.println("Payment successful! Charge ID: " + chargeId);
        } catch (StripeException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }

    private static Token createToken() throws StripeException {
        Map<String, Object> tokenParams = new HashMap<>();
        Map<String, Object> cardParams = new HashMap<>();

        cardParams.put("number", "4242424242424242");
        cardParams.put("exp_month", 12);
        cardParams.put("exp_year", 2023);
        cardParams.put("cvc", "123");

        tokenParams.put("card", cardParams);

        return Token.create(tokenParams);
    }

    private static String createCharge(Token token) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", 1000); // Amount in cents
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "Example charge");
        chargeParams.put("source", token.getId());

        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }
}
