package surveytest.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JanaApi {

    private String clientId="";
    private String secretKey="";
    private String janaUrl="";
    
    public JanaApi(){
    }
    
    public JanaApi(String clientId, String secretKey, String janaUrl){
        this.clientId=clientId;
        this.secretKey=secretKey;
        this.janaUrl=janaUrl;
    }   
    
    public String getLink(String offerId){
        String data=encode(getLinkData(offerId)); 
        String signature=sign(data);
        return post(data,signature,"jia-request");
    }
    
    public String getLinkData(String offerId){
        long nonce=new Random().nextLong();
        StringBuilder request=new StringBuilder();
        request.append( "{" );
        request.append( "\"method\":\"jia-request\"," );
        request.append( "\"algorithm\":\"HMAC-SHA256\"," );
        request.append( "\"client_id\":\"" + clientId + "\"," );
        request.append( "\"offer\":\"" + offerId + "\"," );
        request.append( "\"timestamp\":\"" + System.currentTimeMillis() + "\"," );
        request.append( "\"nonce\":\"" + nonce + "\"" );
        request.append( "}" );
        return request.toString();
    }

    public String sign(String data){    
        try {        
            SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            Mac m = Mac.getInstance("HmacSHA256");
            m.init(key);
            byte[] output = m.doFinal(data.getBytes());
            return encode(new String(output));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public String encode(String data){
        String encoded=Base64.encode(data);     
        encoded=encoded.replace("-","+");
        encoded=encoded.replace("_","/");
        encoded=encoded.replace("=","");
        return encoded;
    }
        
    public String post(String data, String signature, String method){
        long nonce=new Random().nextLong();
        StringBuilder request=new StringBuilder();
        request.append( "request=" + data);
        request.append( "&sig=" + signature);
        String postData=request.toString();
        String response="";
        try {
            URL url = new URL(janaUrl + method); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false); 
            connection.setRequestMethod("POST"); 
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
            connection.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response+=responseLine;
            }
            in.close();
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
    
    public static void main(String[] args) {
        JanaApi janaApi=new JanaApi("gta2i", "293af117b8f14232ad86099f730629bc", "https://api.jana.com/api/");
        
        String data=janaApi.getLinkData("irl_28f425");
        System.out.println(data);
        
        String link=janaApi.getLink("irl_28f425");
        System.out.println(link);
    }
}   