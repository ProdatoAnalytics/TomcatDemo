package com.prodato.norma.demo.tomcat.otherstuff;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class Personalkostenstatistik {

    private static final String PERSONALKOSTENSTATISTIK_FILE = "/home/dard/tmp/l-0700.000.260";
    private static final String OUTFILE = "/tmp/personalkostenstatistik.csv";

    private final List<KstRecord> ksts;

    public Personalkostenstatistik(final String fileName) throws IOException {
        final String file = FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
        final List<String> lines = Arrays.stream(StringUtils.split(file, "\r\n"))
                .filter(e -> !StringUtils.isBlank(e))
                .collect(Collectors.toList());
        //System.out.println(lines.get(0));
        ksts = new ArrayList<>();
        KstRecord currentKst = null;
        for (final String line : lines) {
            if (line.contains("%") && (currentKst != null)) {
                ksts.add(currentKst);
                currentKst = null;
            }
            if (StringUtils.startsWith(line, "KST.:")) {
                final String kstNumberString = line.substring(5, 12).trim();
                currentKst = new KstRecord(Integer.parseInt(kstNumberString));
            }
            if (currentKst != null) {
                final String nrString = line.substring(39, 42).trim();
                final String desc = line.substring(42, 56).trim();
                String valueString = line.substring(56).trim();
                //                System.out.println(nrString + " | " + desc + " | " + valueString);
                final Integer nr = StringUtils.isBlank(nrString) ? null : Integer.parseInt(nrString);
                int sign;
                if (valueString.endsWith("-")) {
                    sign = -1;
                    valueString = valueString.substring(0, valueString.length() - 1);
                } else {
                    sign = 1;
                }
                final BigDecimal value = new BigDecimal(valueString.replaceAll("\\.", "").replace(",", ".")).multiply(new BigDecimal(sign));
                //                if (sign == 1) {
                //                    System.out.println("   " + nrString + "-" + nr + " | " + desc + " | " + valueString + "-" + value);
                //                }
                currentKst.lines.add(new KstLine(nr, desc, value, currentKst));
            }
        }

    }

    @Override
    public String toString() {
        int i = 1;
        final StringBuilder buf = new StringBuilder();
        for (final KstRecord kstRecord : ksts) {
            for (final KstLine kstLine : kstRecord.lines) {
                buf.append(i++).append(";");
                buf.append(kstLine.toString());
            }
        }
        return buf.toString();
    }

    public static void main(final String[] args) throws IOException {
        final Personalkostenstatistik pks = new Personalkostenstatistik(PERSONALKOSTENSTATISTIK_FILE);
        System.out.println(pks.toString());
        FileUtils.write(new File(OUTFILE), pks.toString(), StandardCharsets.UTF_8);
    }

    private static class KstRecord {

        private final int kst;
        private final List<KstLine> lines;

        public KstRecord(final int kst) {
            this.kst = kst;
            lines = new ArrayList<>();
        }

    }

    private static class KstLine {

        private final Integer nr;
        private final String desc;
        private final BigDecimal value;

        private final KstRecord record;

        public KstLine(final Integer nr, final String desc, final BigDecimal value, final KstRecord record) {
            this.nr = nr;
            this.desc = desc;
            this.value = value;
            this.record = record;
        }

        @Override
        public String toString() {
            final StringBuilder buf = new StringBuilder();
            buf.append(record.kst).append(";");
            if (nr != null) {
                buf.append(nr);
            }
            buf.append(";");
            buf.append(desc).append(";");
            buf.append(value).append("\r\n");

            return buf.toString();
        }

    }

}
