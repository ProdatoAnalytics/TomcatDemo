<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:tns="http://www.example.org/NewXMLSchema"
    xmlns="http://www.w3.org/TR/REC-html40">

    <xsl:output method="html" version="4.01" />

    <xsl:template match="/tns:tips">
        <h3>
            <xsl:value-of select="tns:title" />
        </h3>
        <p>
            <xsl:value-of select="tns:description" />
        </p>
        <ul>
            <xsl:apply-templates
                select="/tns:tips/tns:tip" />
        </ul>
    </xsl:template>

    <xsl:template match="tns:tip">
        <b><xsl:value-of select="./tns:topic" /></b>: <xsl:value-of select="./tns:description" /> (<xsl:apply-templates select="./tns:locations"/>)
    </xsl:template>

    <xsl:template match="tns:locations">
        <b>
            <xsl:for-each select="*">
                <xsl:value-of select="." />
                <xsl:if test="position() != last()">
                    <xsl:text>, </xsl:text>
                </xsl:if>
            </xsl:for-each>
        </b>
    </xsl:template>

</xsl:stylesheet>