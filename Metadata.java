//Add this to the Search Crawler
//this is for getting metadata from the link
//it returns 2d array 
//first one for name and the other for content
//maximum meta data i have found was 6 so I have mafe it 10


private String[][] retrieveMetaData(Document doc)
        {
            //ArrayList linkList = new ArrayList();
            String[][] str=new String[10][2];
            Elements meta=doc.select("meta");
            for(int i=0;i<meta.size();i++) 
            {
                str[i][0]=meta.attr("name");
                str[i][1]=meta.attr("content");
            }
            return str;  
        }
