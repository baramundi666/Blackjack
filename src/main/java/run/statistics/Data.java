package run.statistics;

import model.elementary.Result;

public class Data {
    private  Result result;
    public Data(Result result) {
        this.result = result;
    }
    public Data() {}

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
