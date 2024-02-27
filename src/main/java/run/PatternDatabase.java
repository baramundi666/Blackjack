package run;

public class PatternDatabase {

    public static final double[] standardInsurance = {1.4, 2.4, 2.7, 2.9, 3.0, 3.0, 3.1, 3.1, 3.3};
    public static final String[][] newPattern = {
            // 2     3     4     5     6     7     8     9    10     A
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //5
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //6
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //7
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //8
            {"HH0","DD0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //9
            {"DD0","DD0","DD0","DD0","DD0","DD0","DD0","DD0","HH0","HH0"}, //10
            {"DD0","DD0","DD0","DD0","DD0","DD0","DD0","DD0","DD0","HH0"}, //11
            {"HH0","HH0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //12
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //13
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //14
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","HH0","UU0","UU0"}, //15
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","UU0","UU0","UU0"}, //16
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","UU0"}, //17
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //18
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //19
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //20
            {"HH0","HH0","HH0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-2
            {"HH0","HH0","HH0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-3
            {"HH0","HH0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-4
            {"HH0","HH0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-5
            {"HH0","DD0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-6
            {"SS0","DD0","DD0","DD0","DD0","SS0","SS0","HH0","HH0","HH0"}, //A-7
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //A-8
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //A-9
            {"PP0","PP0","PP0","PP0","PP0","PP0","PP0","PP0","PP0","HH0"}, //A-A
            {"PP0","PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0"}, //2-2
            {"PP0","PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0"}, //3-3
            {"HH0","HH0","HH0","PP0","PP0","HH0","HH0","HH0","HH0","HH0"}, //4-4
            {"DD0","DD0","DD0","DD0","DD0","DD0","DD0","DD0","HH0","HH0"}, //5-5
            {"PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0","HH0"}, //6-6
            {"PP0","PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0"}, //7-7
            {"PP0","PP0","PP0","PP0","PP0","PP0","PP0","PP0","HH0","UU0"}, //8-8
            {"PP0","PP0","PP0","PP0","PP0","SS0","PP0","PP0","SS0","SS0"}, //9-9
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //10-10
    };

    public static final String[][] basicStrategy = {
            // 2     3     4     5     6     7     8     9    10     A
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //5
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //6
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //7
            {"HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0","HH0"}, //8
            {"HH0","DD0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //9
            {"DD0","DD0","DD0","DD0","DD0","DD0","DD0","DD0","HH0","HH0"}, //10
            {"DD0","DD0","DD0","DD0","DD0","DD0","DD0","DD0","HH0","HH0"}, //11
            {"HH0","HH0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //12
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //13
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //14
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //15
            {"SS0","SS0","SS0","SS0","SS0","HH0","HH0","HH0","HH0","HH0"}, //16
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //17
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //18
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //19
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //20
            {"HH0","HH0","HH0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-2
            {"HH0","HH0","HH0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-3
            {"HH0","HH0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-4
            {"HH0","HH0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-5
            {"HH0","DD0","DD0","DD0","DD0","HH0","HH0","HH0","HH0","HH0"}, //A-6
            {"SS0","DD0","DD0","DD0","DD0","SS0","SS0","HH0","HH0","HH0"}, //A-7
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //A-8
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //A-9
            {"PP0","PP0","PP0","PP0","PP0","PP0","PP0","PP0","PP0","HH0"}, //A-A
            {"PP0","PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0"}, //2-2
            {"PP0","PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0"}, //3-3
            {"HH0","HH0","HH0","PP0","PP0","HH0","HH0","HH0","HH0","HH0"}, //4-4
            {"DD0","DD0","DD0","DD0","DD0","DD0","DD0","DD0","HH0","HH0"}, //5-5
            {"PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0","HH0"}, //6-6
            {"PP0","PP0","PP0","PP0","PP0","PP0","HH0","HH0","HH0","HH0"}, //7-7
            {"PP0","PP0","PP0","PP0","PP0","PP0","PP0","PP0","HH0","HH0"}, //8-8
            {"PP0","PP0","PP0","PP0","PP0","SS0","PP0","PP0","SS0","SS0"}, //9-9
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //10-10
    };

    public static final String[][] basicStrategyWithCardCounting = {
            // "Professional Blackjack" by Stanford Wong
            // Lose All to a Natural, S17
            // 2     3     4      5      6      7     8     9    10     A
            {"HH0","HH0","HH0" ,"HH0" ,"HH0" ,"HH0","HH0","HH0","HH0","HH0"}, //5
            {"HH0","HH0","HH0" ,"HH0" ,"HH0" ,"HH0","HH0","HH0","HH0","HH0"}, //6
            {"HH0","HH0","HH0" ,"HD9" ,"HD9" ,"HH0","HH0","HH0","HH0","HH0"}, //7
            {"HH0","HD9","HD5" ,"HD3" ,"HD1" ,"HH0","HH0","HH0","HH0","HH0"}, //8
            {"HD1","HD0","HD-2","HD-4","HD-6","HD3","HD7","HH0","HH0","HH0"}, //9
            {"HD-8","HD-9","HD-10","DD0","DD0" ,"HD-6","HD-4","HD-1" ,"HH0" ,"HH0"}, //10
            {"DD0" ,"DD0" ,"DD0" ,"DD0" ,"DD0" ,"HD-9","HD-6","HD-4" ,"HD3" ,"HH0"}, //11
            {"HS3" ,"HS2" ,"HS0" ,"HS-1","HS0" ,"HH0" ,"HH0" ,"HH0" ,"HH0" ,"HH0"}, //12
            {"HS0" ,"HS-1","HS-3","HS-4","HS-4","HH0" ,"HH0" ,"HH0" ,"HH0" ,"HH0"}, //13
            {"HS-3","HS-4","HS-6","HS-7","HS-7","HH0" ,"HH0" ,"HH0" ,"HH0" ,"HH0"}, //14
            {"HS-5","HS-6","HS-7","HS-9","HS-9","HS10","HU7","HU2" ,"HU-2","UU0"}, //15 SURRENDER SINCE HERE
            {"HS-8","HS-10","SS0","SS0" ,"SS0" ,"HS9" ,"HU4" ,"HU0" ,"SU-5"  ,"UU0"}, //16
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0" ,"SU5"   ,"UU0"}, //17
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //18
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //19
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //20
            // 2     3      4      5      6        7     8     9    10     A
            {"HH0","HD7" ,"HD3" ,"HD0", "HD-1"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-2
            {"HH0","HD7" ,"HD1" ,"HD-1","HD-4"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-3
            {"HH0","HD7" ,"HD0" ,"HD-4" ,"HD-9" ,"HH0","HH0","HH0","HH0","HH0"}, //A-4
            {"HH0","HD4" ,"HD-2","HD-6" ,"DD0"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-5
            {"HD1","HD-3","HD-7","HD-10","DD0"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-6
            {"SD0","SD-2","SD-6","SD-8" ,"SD-10","SS0","SS0","HH0","HH0","HS1"}, //A-7
            {"SD8","SD5" ,"SD3" ,"SD1"  ,"SD1"  ,"SS0","SS0","SS0","SS0","SS0"}, //A-8
            {"SD10","SD8","SD6" ,"SD5"  ,"SD4"  ,"SS0","SS0","SS0","SS0","SS0"}, //A-9
            // 2      3      4      5      6     7      8       9     10     A
            {"PP0" ,"PP0"  ,"PP0" ,"PP0" ,"PP0" ,"HP-9","HP-8"  ,"HP-7","HP-6","HH0"}, //A-A ???
            {"HP-3","HP-5","HP-7","HP-9","PP0", "PP0" ,"HP5"  ,"HH0" ,"HH0" ,"HH0"}, //2-2
            {"HP0" ,"HP-4" ,"HP-7","HP-9","PP0", "PP0","HP4"  ,"HH0" ,"HH0" ,"HH0"}, //3-3
            {"HH0" ,"HP6"  ,"HP1" ,"HP-1","HP-4","HH0" ,"HH0"  ,"HH0" ,"HH0" ,"HH0"}, //4-4 (DAS)
            {"HD-8","HD-9","HD-10","DD0" ,"DD0" ,"HD-6","HD-4","HD-1" ,"HH0" ,"HH0"}, //5-5
            {"HP-1","HP-4","HP-6","HP-8" ,"HP-10","HH0","HH0" ,"HH0" ,"HH0" ,"HH0"}, //6-6
            {"HP-9","PP0" ,"PP0" ,"PP0"  ,"PP0"  ,"PP0","HP+5","HH0" ,"HH0" ,"HH0"}, //7-7
            {"PP0","PP0"  ,"PP0" ,"PP0"  ,"PP0"  ,"PP0","PP0" ,"PU7" ,"PU-2","UU0"}, //8-8
            {"SP-2","SP-3","SP-5","SP-6" ,"SP-6" ,"SP3","SP-8","SP-9","SS0" ,"SS0"}, //9-9
            {"SS0" ,"SP8" ,"SP6" ,"SP5"  ,"SP4"  ,"SS0","SS0" ,"SS0" ,"SS0" ,"SS0"}, //10-10
    };

    public static final String[][] basicStrategyWithCardCountingBenchmark = {
            // "Professional Blackjack" by Stanford Wong
            // 2     3     4      5      6      7     8     9    10     A
            {"HH0","HH0","HH0" ,"HH0" ,"HH0" ,"HH0","HH0","HH0","HH0","HH0"}, //5
            {"HH0","HH0","HH0" ,"HH0" ,"HH0" ,"HH0","HH0","HH0","HH0","HH0"}, //6
            {"HH0","HH0","HH0" ,"HD9" ,"HD9" ,"HH0","HH0","HH0","HH0","HH0"}, //7
            {"HH0","HD9","HD5" ,"HD3" ,"HD1" ,"HH0","HH0","HH0","HH0","HH0"}, //8
            {"HD1","HD0","HD-2","HD-4","HD-6","HD3","HD7","HH0","HH0","HH0"}, //9
            {"HD-8","HD-9","HD-10","DD0","DD0","HD-6","HD-4","HD-1","HD4","HD4"}, //10
            {"DD0" ,"DD0" ,"DD0" ,"DD0" ,"DD0" ,"HD-9","HD-6","HD-4","HD-4","HD+1"}, //11
            {"HS3" ,"HS2" ,"HS0" ,"HS-1","HS0" ,"HH0" ,"HH0" ,"HH0" ,"HH0" ,"HH0"}, //12
            {"HS0" ,"HS-1","HS-3","HS-4","HS-4","HH0" ,"HH0" ,"HH0" ,"HH0" ,"HH0"}, //13
            {"HS-3","HS-4","HS-6","HS-7","HS-7","HH0" ,"HH0" ,"HH0" ,"HH0" ,"HH0"}, //14
            {"HS-5","HS-6","HS-7","HS-9","HS-9","HS10" ,"HS10" ,"HS8" ,"HS4" ,"HS10"}, //15
            {"HS-8","HS-10","SS0","SS0" ,"SS0" ,"HS9" ,"HU4" ,"HU0" ,"SU-5"  ,"UU0"}, //16
            {"HS-3","HS-4","HS-6","HS-7","HS-7","SS0" ,"SS0" ,"SS0" ,"SU5"   ,"UU0"}, //17
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //18
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //19
            {"SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0","SS0"}, //20
            // 2     3      4      5      6        7     8     9    10     A
            {"HH0","HD7" ,"HD3" ,"HD0", "HD-1"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-2
            {"HH0","HD7" ,"HD1" ,"HD-1","HD-4"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-3
            {"HH0","HD7" ,"HD0" ,"HD-4" ,"HD-9" ,"HH0","HH0","HH0","HH0","HH0"}, //A-4
            {"HH0","HD4" ,"HD-2","HD-6" ,"DD0"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-5
            {"HD1","HD-3","HD-7","HD-10","DD0"  ,"HH0","HH0","HH0","HH0","HH0"}, //A-6
            {"SD0","SD-2","SD-6","SD-8" ,"SD-10","SS0","SS0","HH0","HH0","HS1"}, //A-7
            {"SD8","SD5" ,"SD3" ,"SD1"  ,"SD1"  ,"SS0","SS0","SS0","SS0","SS0"}, //A-8
            {"SD10","SD8","SD6" ,"SD5"  ,"SD4"  ,"SS0","SS0","SS0","SS0","SS0"}, //A-9
            // 2      3      4      5      6     7      8       9     10     A
            {"PP0" ,"PP0" ,"PP0" ,"PP0" ,"PP0","HP-9","HP-8"  ,"HP-7","HP-8","HH0"}, //A-A ???
            {"HP-3","HP-5","HP-7","HP-9","PP0", "PP0" ,"HP5"  ,"HH0" ,"HH0" ,"HH0"}, //2-2
            {"HP0" ,"HP-4" ,"HP-7","HP-9","PP0", "PP0","HP4"  ,"HH0" ,"HH0" ,"HH0"}, //3-3
            {"HH0" ,"HP6"  ,"HP1" ,"HP-1","HP-4","HH0","HH0"  ,"HH0" ,"HH0" ,"HH0"}, //4-4 (DAS)
            {"HD-8","HD-9" ,"HD-10","DD0","DD0" ,"HD-6","HD-4","HD-1","HD4" ,"HD4"}, //5-5
            {"HP-1","HP-4","HP-6","HP-8" ,"HP-10","HH0","HH0" ,"HH0" ,"HH0" ,"HH0"}, //6-6
            {"HP-9","PP0" ,"PP0" ,"PP0"  ,"PP0"  ,"PP0","HP+5","HH0" ,"HH0" ,"HH0"}, //7-7
            {"PP0","PP0"  ,"PP0" ,"PP0"  ,"PP0"  ,"PP0","PP0" ,"PU7" ,"PU-2","UU0"}, //8-8
            {"SP-2","SP-3","SP-5","SP-6" ,"SP-6" ,"SP3","SP-8","SP-9","SS0" ,"SP3"}, //9-9
            {"SS0" ,"SP8" ,"SP6" ,"SP5"  ,"SP4"  ,"SS0","SS0" ,"SS0" ,"SS0" ,"SS0"}, //10-10
    };
}
