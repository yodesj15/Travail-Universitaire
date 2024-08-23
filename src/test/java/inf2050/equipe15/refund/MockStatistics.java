package inf2050.equipe15.refund;

import com.google.gson.JsonObject;

public class MockStatistics extends Statistics{
    private JsonObject jsonObjStatsFile = createDefaultStatsJsonObject();

    @Override
    protected String setStatsFileDefaultLocation() {
        return "./StatsDefaultLocation.json";
    }

    @Override
    protected void writeStatsToDataFile(JsonObject jsonObject) {
        jsonObjStatsFile = jsonObject;
    }

    @Override
    protected JsonObject readStatsFromDataFileAsJsonObject(){
        return verifyStatsJsonObject(jsonObjStatsFile)
                ? jsonObjStatsFile
                : createDefaultStatsJsonObject();
    }

    /**
     * Returns the JsonObject that acts as the statistics file.
     * @return the JsonObject that acts as the statistics file.
     */
    public JsonObject getJsonObjStatsFile() {
        return jsonObjStatsFile;
    }
}
