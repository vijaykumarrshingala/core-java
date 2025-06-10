MERGE INTO your_table AS target
USING (SELECT ? AS symbol, ? AS col2, ? AS col3, ? AS col4, 
              ? AS col5, ? AS col6, ? AS col7, ? AS col8) AS source
ON (target.symbol = source.symbol)
WHEN MATCHED THEN
    UPDATE SET col2=source.col2, col3=source.col3, col4=source.col4,
               col5=source.col5, col6=source.col6, col7=source.col7, col8=source.col8
WHEN NOT MATCHED THEN
    INSERT (symbol, col2, col3, col4, col5, col6, col7, col8)
    VALUES (source.symbol, source.col2, source.col3, source.col4, 
            source.col5, source.col6, source.col7, source.col8)
