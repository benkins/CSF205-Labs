<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<ol>
					<xsl:for-each select="Course_Catalog/Department">
						<li>
							<xsl:value-of select="Title"/>
							<ol>
								<xsl:for-each select="Chair/Professor">
									<li>
										<p>
											<xsl:value-of select="node()"/>
										</p>
									</li>
								</xsl:for-each>
							</ol>
						</li>
					</xsl:for-each>
				</ol>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>