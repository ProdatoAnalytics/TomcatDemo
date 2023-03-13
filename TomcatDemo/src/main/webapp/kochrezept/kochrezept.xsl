<?xml version="1.0" encoding="ISO-8859-1" ?>

<xsl:stylesheet version="1.0" 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

   <!-- Hauptelement: Header -->
   <xsl:template match="/MAIN">
      <!-- Variablen definieren -->
      <xsl:variable name="buttondir" select="@buttons"/>
      <xsl:variable name="imagedir" select="@images"/>
<html>
<head>

<link rel="stylesheet" type="text/css" href="kochrezept.css"/>
  
<title>
      <xsl:value-of select="@title"/>
</title>
</head>
<body>
<TABLE><TR><TD class="noborder">
<H2>
      <xsl:value-of select="@title"/>
</H2>


      <xsl:for-each select="KOMMENTARE">
         <xsl:apply-templates>
            <xsl:with-param name="buttondir" select="$buttondir"/>
            <xsl:with-param name="imagedir" select="$imagedir"/>         
         </xsl:apply-templates>
<!--         <xsl:call-template name="kommentar">
            <xsl:with-param name="buttondir" select="$buttondir"/>
            <xsl:with-param name="imagedir" select="$imagedir"/>
         </xsl:call-template>-->
      </xsl:for-each>
      
      <xsl:for-each select="INHALT">
         <xsl:call-template name="inhalt"/>
      </xsl:for-each>
      
      
<TABLE class="tagebuch">
      <xsl:element name="A">
         <xsl:attribute name="name"><xsl:value-of select="TAGEBUCH/@linkid"/></xsl:attribute>
      </xsl:element>
      <xsl:for-each select="TAGEBUCH/EINTRAG">
         <xsl:call-template name="eintrag"/>
      </xsl:for-each>
</TABLE>
      <xsl:for-each select="FAECHER/FACH">
         <xsl:call-template name="fach">
            <xsl:with-param name="buttondir" select="$buttondir"/>
            <xsl:with-param name="imagedir" select="$imagedir"/>
         </xsl:call-template>
      </xsl:for-each>
      
<H4>
      <xsl:element name="A">
         <xsl:attribute name="name"><xsl:value-of select="QUELLEN/@linkid"/></xsl:attribute>
         Quellen:
      </xsl:element>
</H4>

<SPAN class="quelle">
      <xsl:for-each select="QUELLEN/QUELLE">
         <xsl:call-template name="quelle"/>
      </xsl:for-each>
</SPAN>

<!-- 
      <xsl:for-each select="*">
         <xsl:if test="../FACH">
            <xsl:call-template name="fach">
               <xsl:with-param name="buttondir" select="$buttondir"/>
               <xsl:with-param name="imagedir" select="$imagedir"/>
            </xsl:call-template>WWW
         </xsl:if>
         <xsl:if test="../KOMMENTAR">
            <xsl:value-of select="node()"/>
         </xsl:if>
      </xsl:for-each> -->
</TD></TR></TABLE>
</body>
</html>
   </xsl:template>

   <!-- KOMMENTAR -->
   <xsl:template match="KOMMENTAR">
<P>
      <xsl:apply-templates/>
</P>
   </xsl:template>
   
   <!-- LINK -->
   <xsl:template match="LINK">
      <xsl:element name="A">
         <xsl:attribute name="HREF"><xsl:value-of select="@href"/></xsl:attribute>
         <xsl:value-of select="."/>
      </xsl:element>         
   </xsl:template>
   
   
   <!-- FACH -->
   <xsl:template name="fach">
      <xsl:param name="buttondir">.</xsl:param>
      <xsl:param name="imagedir">.</xsl:param>
<HR/>
<H3>
      <xsl:element name="A">
         <xsl:attribute name="name"><xsl:value-of select="@linkid"/></xsl:attribute>
         <xsl:value-of select="@name"/>
      </xsl:element>
</H3>
<TABLE class="toptable">
      <xsl:for-each select="KAPITEL">
         <xsl:call-template name="kapitel">
            <xsl:with-param name="buttondir" select="$buttondir"/>
            <xsl:with-param name="imagedir" select="$imagedir"/>
         </xsl:call-template>
      </xsl:for-each>
</TABLE>
   </xsl:template>


   <!-- KAPITEL -->
   <xsl:template name="kapitel">
      <xsl:param name="buttondir">.</xsl:param>
      <xsl:param name="imagedir">.</xsl:param>
<TR><TD class="noborder">
<TABLE>
<H5>
   <xsl:value-of select="@name"/>
</H5>
      <xsl:for-each select="BLATT">
         <xsl:call-template name="blatt">
            <xsl:with-param name="buttondir" select="$buttondir"/>
            <xsl:with-param name="imagedir" select="$imagedir"/>
         </xsl:call-template>
      </xsl:for-each>
</TABLE>
</TD></TR>
   </xsl:template>


   <!-- BLATT -->
   <xsl:template name="blatt">
      <xsl:param name="buttondir">.</xsl:param>
      <xsl:param name="imagedir">.</xsl:param>
<TR>
<TD class="spaceholder" />
<TD class="buttonwidth">
      <xsl:call-template name="rang">
         <xsl:with-param name="buttondir" select="$buttondir"/>
      </xsl:call-template>
</TD>
<TD>
      <xsl:call-template name="link">
         <xsl:with-param name="imagedir" select="$imagedir"/>
      </xsl:call-template>
      <xsl:if test="@status">
         <xsl:text>  </xsl:text>
<SPAN class="status">
         <xsl:value-of select="@status"/>
</SPAN>
      </xsl:if>
<BR/>
<DIV>
      <xsl:apply-templates/>
</DIV>
</TD>

</TR>
   </xsl:template>


   <!-- BUTTONS -->
   <xsl:template name="rang">
      <xsl:param name="buttondir">.</xsl:param>
      <xsl:choose>
         <!-- Grüner Button -->
         <xsl:when test="@rang='gruen'">
            <xsl:element name="IMG">
               <xsl:attribute name="src"><xsl:value-of select="$buttondir"/>green.gif</xsl:attribute>
               <xsl:attribute name="alt">nicht wichtig</xsl:attribute>
               <xsl:attribute name="width">15</xsl:attribute>
               <xsl:attribute name="height">15</xsl:attribute>
            </xsl:element>
         </xsl:when>
         <!-- Gelber Button -->
         <xsl:when test="@rang='gelb'">
            <xsl:element name="IMG">
               <xsl:attribute name="src"><xsl:value-of select="$buttondir"/>yellow.gif</xsl:attribute>
               <xsl:attribute name="alt">anschauen</xsl:attribute>
               <xsl:attribute name="width">15</xsl:attribute>
               <xsl:attribute name="height">15</xsl:attribute>
            </xsl:element>
         </xsl:when>
         <!-- Roter Button -->
         <xsl:when test="@rang='rot'">
            <xsl:element name="IMG">
               <xsl:attribute name="src"><xsl:value-of select="$buttondir"/>red.gif</xsl:attribute>
               <xsl:attribute name="alt">wichtig</xsl:attribute>
               <xsl:attribute name="width">15</xsl:attribute>
               <xsl:attribute name="height">15</xsl:attribute>
            </xsl:element>
         </xsl:when>
         <!-- Grauer Button -->
         <xsl:when test="@rang='grau'">
            <xsl:element name="IMG">
               <xsl:attribute name="src"><xsl:value-of select="$buttondir"/>grey.gif</xsl:attribute>
               <xsl:attribute name="alt">neutral</xsl:attribute>
               <xsl:attribute name="width">15</xsl:attribute>
               <xsl:attribute name="height">15</xsl:attribute>
            </xsl:element>
         </xsl:when>
         <xsl:otherwise>
            <xsl:element name="IMG">
               <xsl:attribute name="src"><xsl:value-of select="$buttondir"/>transparent.gif</xsl:attribute>
               <xsl:attribute name="alt">unbekannt</xsl:attribute>
               <xsl:attribute name="width">15</xsl:attribute>
               <xsl:attribute name="height">15</xsl:attribute>
            </xsl:element>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>


   <!-- LINKS -->
   <xsl:template name="link">
      <xsl:param name="imagedir">.</xsl:param>
      
      <xsl:choose>
         <!-- Link -->
         <xsl:when test="@link">
            <xsl:element name="A">
               <xsl:attribute name="HREF"><xsl:value-of select="$imagedir"/><xsl:value-of select="@link"/></xsl:attribute>
               <xsl:value-of select="@name"/>
            </xsl:element>         
         </xsl:when>
         <!-- Kein Link: einfacher Text -->
         <xsl:otherwise>
            <xsl:value-of select="@name"/>
         </xsl:otherwise>
      </xsl:choose>
   </xsl:template>

   <!-- TAGEBUCH/EINTRAG -->
   <xsl:template name="eintrag">
<TR>
<TD class="tagebuchdatum" valign="top">
      <xsl:value-of select="@datum"/><BR/>
</TD>
<TD class="tagebuch">
      <xsl:apply-templates/>
</TD>
</TR>
   </xsl:template>

   <!-- ZEILE (im Tagebucheintrag) -->
   <xsl:template match="ZEILE">
      <P><xsl:apply-templates/></P>
   </xsl:template>
   
   <!-- QUELLE -->
   <xsl:template name="quelle">
      <P><xsl:apply-templates/></P>
   </xsl:template>
   
   <!-- INHALT -->
   <xsl:template name="inhalt">
<TABLE class="inhalttable">
<TR><TD class="inhaltspaceholder"></TD><TD>
<UL>
      <xsl:for-each select="INHALTEINTRAG">
<LI>
         <xsl:element name="A">
            <xsl:attribute name="HREF">#<xsl:value-of select="@link"/>
            </xsl:attribute>
            <xsl:value-of select="."/>
         </xsl:element>
</LI>
      </xsl:for-each>
</UL>
</TD><TD class="inhaltspaceholder"></TD></TR>
</TABLE>
   </xsl:template>
   
   
</xsl:stylesheet>
