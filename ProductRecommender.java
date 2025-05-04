import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;

import java.io.File;
import java.util.List;

public class ProductRecommender {

    public static void main(String[] args) {
        try {
            // 1. Load the data model
            DataModel model = new FileDataModel(new File("preferences.csv"));

            // 2. Create a UserSimilarity
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // 3. Create a UserNeighborhood
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model); // Consider 2 nearest users

            // 4. Create a UserBasedRecommender
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // 5. Get recommendations for a user
            long userId = 1; // User ID for whom to generate recommendations
            int numberOfRecommendations = 5; // Number of recommendations to generate

            List<RecommendedItem> recommendations = recommender.recommend(userId, numberOfRecommendations);

            // 6. Display the recommendations
            System.out.println("Recommendations for User " + userId + ":");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item ID: " + recommendation.getItemID() + ", Estimated Preference: " + recommendation.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}