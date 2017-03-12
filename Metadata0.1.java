private String[][] retrieveHeader(Document doc)
        {
            int count=0;
            //ArrayList linkList = new ArrayList();
            String[][] str=new String[10][2];
            for(Element meta : doc.select("meta")) 
            {
                str[count][0]=meta.attr("name");
                str[count][1]=meta.attr("content");
                 
                count++;
            }
            return str;  
        }
