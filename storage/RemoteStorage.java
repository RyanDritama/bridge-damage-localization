package com.tugas_akhir.sink.storage;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import com.tugas_akhir.sink.wsn.StatusSensor;
import java.sql.Timestamp;

public class RemoteStorage {
    public RemoteStorage(int idJembatan, String url, String username, String password) throws SQLException, ClassNotFoundException{
        this.idJembatan = idJembatan;

        String file_ks = "/home/up/.keystore";
        String password_ks = "jOk<>123";
        System.setProperty("javax.net.ssl.trustStore", file_ks);
        System.setProperty("javax.net.ssl.trustStorePassword", password_ks);
        
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("DroneGPS", "drone1234");
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        client.register(feature);
        WebTarget webTarget = client.target(url);
        WebTarget userTarget = webTarget.path("BridgeData/");
        invocationBuilderPost = userTarget.request(MediaType.APPLICATION_JSON);
        
        WebTarget getTarget = webTarget.path("parameter/"+ String.valueOf(idJembatan)+"/");
        invocationBuilderGet = getTarget.request(MediaType.APPLICATION_JSON);
        WebTarget statusTarget = webTarget.path("Sensor/");
        invocationBuilderPostStatus = statusTarget.request(MediaType.APPLICATION_JSON);
    }
    
    public void write(DataJembatan data) throws SQLException{
        RestBridgeData restData = new RestBridgeData();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        
        restData.date = dateFormat.format(data.date);
        restData.bridge = idJembatan;
        restData.health_index = data.indeksKesehatan;
        restData.mean_frequency = data.meanFrequency;
        restData.mode_shape = Arrays.asList(data.modeShape) ;
                restData.value = Arrays.asList(data.value);
        restData.bridge_crack = Arrays.asList(data.lokasiKerusakan);
        restData.bridge_sensor = Arrays.asList(data.bridgeSensor);
        Float[] frequency = new Float[10];
        for (int i = 0; i<data.frequency.length; i++){
            frequency[i] = (Float) data.frequency[i];
        }
        restData.frequency = Arrays.asList(frequency);
        Response response = invocationBuilderPost.post(Entity.json(restData));
    }
    
    public float getIdealFreq() throws SQLException{
        Response response = invocationBuilderGet.get();
        RestParameter param = response.readEntity(RestParameter.class);
        return param.ideal_frequency_Hz;
    }
    
    public void sendStatus(StatusSensor sensor){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        RestSensor rest = new RestSensor();
        Timestamp date = new Timestamp(0);
        date.setTime(System.currentTimeMillis());
        rest.date = dateFormat.format(date);
        rest.status = Arrays.asList(sensor.statusSensor);
        rest.bridge =1;
        Response response = invocationBuilderPostStatus.post(Entity.json(rest));
        
    }
            
    final int idJembatan;
    final Invocation.Builder invocationBuilderPost,invocationBuilderGet, invocationBuilderPostStatus;
}
