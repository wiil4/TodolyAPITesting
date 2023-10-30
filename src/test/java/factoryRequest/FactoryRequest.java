package factoryRequest;

public class FactoryRequest {
    public static IRequest make(String type){
        IRequest request;
        switch(type.toLowerCase()){
            case "get":
                request = new GETRequest();
                break;
            case "post":
                request = new POSTRequest();
                break;
            case "put":
                request = new PUTRequest();
                break;
            case "delete":
                request = new DELETERequest();
                break;
            default:
                try {
                    throw new Exception("Method not implemented");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        return request;
    }
}
