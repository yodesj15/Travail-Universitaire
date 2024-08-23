package inf2050.equipe15.refund;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {
    @Test
    void resetStats() {
        MockStatistics mockStatistics = new MockStatistics();
        mockStatistics.resetStats();
        assertEquals(mockStatistics.createDefaultStatsJsonObject(), mockStatistics.getJsonObjStatsFile());
    }

    @Test
    void incrementRejectedClaim() {
        MockStatistics mockStatistics = new MockStatistics();
        mockStatistics.incrementRejectedClaim();
        assertEquals(1, mockStatistics.getJsonObjStatsFile().get("rejected").getAsInt());
    }

    @Test
    void incrementSuccessfulClaim() {
        MockStatistics mockStatistics = new MockStatistics();
        int[] careNumbersToAdd = new int[]{100, 200, 300};
        for (int careNumber : careNumbersToAdd) {
            mockStatistics.addCareNumber(careNumber);
        }
        mockStatistics.incrementSuccessfulClaim();
        assertEquals(1, mockStatistics.getJsonObjStatsFile().get("treated").getAsInt());
        assertEquals(3, mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject().keySet().size());
        for (int careNumber : careNumbersToAdd) {
            assertTrue(mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject().has(String.valueOf(careNumber)));
            assertEquals(1, mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject()
                                    .get(String.valueOf(careNumber)).getAsInt());
        }
    }

    @Test
    void incrementSuccessfulClaimWithMultipleOfSameCareNumber() {
        MockStatistics mockStatistics = new MockStatistics();
        int[] careNumbersToAdd = new int[]{200, 200, 300};
        for (int careNumber : careNumbersToAdd) {
            mockStatistics.addCareNumber(careNumber);
        }
        mockStatistics.incrementSuccessfulClaim();
        assertEquals(1, mockStatistics.getJsonObjStatsFile().get("treated").getAsInt());
        assertEquals(2, mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject().keySet().size());

        assertTrue(mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject().has("200"));
        assertEquals(2, mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject()
                    .get("200").getAsInt());

        assertTrue(mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject().has("300"));
        assertEquals(1, mockStatistics.getJsonObjStatsFile().get("cares").getAsJsonObject()
                .get("300").getAsInt());

    }
}