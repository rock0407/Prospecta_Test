package com.example.CSV;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CSVService {
    private final Map<String, Double> mapValues = new HashMap<>();

    public String processCSV(String csvStr) {
        String[] rows = csvStr.split(", ");
        for (String itr : rows) {
            String[] strData = itr.split(": ");
            if (strData.length != 2) {
                throw new IllegalArgumentException("Invalid format for entry: " + itr);
            }
            String strName = strData[0].trim();
            String strValue = strData[1].trim();
            mapValues.put(strName, evaluateCell(strValue));
        }

        // Construct the output CSV with evaluated values
        StringBuilder strBuilder = new StringBuilder("|  | A | B | C |\n| --- | --- | --- | --- |\n");
        strBuilder.append("| 1 | ").append(mapValues.get("A1")).append(" | ").append(mapValues.get("B1")).append(" | ").append(mapValues.get("C1")).append(" |\n");
        strBuilder.append("| 2 | ").append(mapValues.get("A2")).append(" | ").append(mapValues.get("B2")).append(" | ").append(mapValues.get("C2")).append(" |\n");
        strBuilder.append("| 3 | ").append(mapValues.get("A3")).append(" | ").append(mapValues.get("B3")).append(" | ").append(mapValues.get("C3")).append(" |\n");

        return strBuilder.toString();
    }


    // Method to evaluate individual cell value or formula
    private Double evaluateCell(String value) {
        if (value.startsWith("=")) {
            return evaluateFormula(value.substring(1));
        } else {
            return Double.parseDouble(value);
        }
    }

    // Method to evaluate simple formulas like "=A1+B1"
    private Double evaluateFormula(String formula) {
        String[] operands = formula.split("\\+");
        double result = 0;

        for (String operand : operands) {
            operand = operand.trim();

            if (mapValues.containsKey(operand)) {
                result += mapValues.get(operand);
            } else {
                result += Double.parseDouble(operand);
            }
        }

        return result;
    }
}
