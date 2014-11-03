package surveytest.utils;

import java.util.Random;

public class JanaApi {

    public static String DEFAULT_API_URL = "https://api.jana.com/api/";
    public static long MAX_NONCE = 999999999;

    private String clientId="";
    private String secretKey="";
    private String url="";
    
    public JanaApi(){
    }
    
    public JanaApi(String clientId, String secretKey, String url){
        this.clientId=clientId;
        this.secretKey=secretKey;
        this.url=url;
    }   
    
    public String getLinkData(String offerId){
        long nonce=new Random().nextLong();
        StringBuilder request=new StringBuilder();
        request.append( "{" );
        request.append( "\"method\":\"jia-request\"," );
        request.append( "\"algorithm\":\"HMAC-SHA256\"," );
        request.append( "\"clientId\":\"" + clientId + "\"," );
        request.append( "\"offer\":\"" + offerId + "\"," );
        request.append( "\"timestamp\":\"" + System.currentTimeMillis() + "\"," );
        request.append( "\"nonce\":\"" + nonce + "\"" );
        request.append( "}" );
        return request.toString();
    }

    public String getLink(String offerId){
        String data=getLinkData(offerId); 
        
        String encoded=Base64.encode(data);     
        encoded=encoded.replace("-","+");
        encoded=encoded.replace("_","/");
         
        return encoded;
    }
    
    public static void main(String[] args) {
        JanaApi janaApi=new JanaApi("testClientId", "testKey", "testUrl");
        
        String data=janaApi.getLinkData("testOffer");
        System.out.println(data);
        
        String link=janaApi.getLink("testOffer");
        System.out.println(link);
    }
}   
    
/*
    def get_jia_link(self, offer_id):
            
        data = self.base_request_data()
        data['method'] = 'jia-request'
        data['offer'] = offer_id

        encoded = self.encode_data(data)
        sig = self.sign_data(encoded)

        response = self.post_to_jana(encoded, sig, 'jia-request')
        
        return response

    def sign_data(self, data):
        sig = hmac.new(self.secret_key, msg=data, digestmod=hashlib.sha256).digest()
        encoded_sig = base64.urlsafe_b64encode(sig).rstrip('=')
        return encoded_sig        

    def post_to_jana(self, encoded, sig, method):
        url = self.api_url + method
        payload = { 'request' : encoded, 'sig' : sig }
        r = requests.post(url, data=payload)
        return r.json()
*/            
            
            
 