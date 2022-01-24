package br.org.creathus.demomqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class Mqtt {

    private static final String MQTT_PUBLISHER_ID = "spring-server";
    private static String MQTT_SERVER_ADDRES;
    private static IMqttClient instance;

    private Mqtt() {
    }

    public IMqttClient getInstance() {

        if (instance == null) {

            try {
                InputStream output = getClass().getClassLoader().getResourceAsStream("application.properties");

                Properties prop = new Properties();
                prop.load(output);

                MQTT_SERVER_ADDRES = prop.getProperty("host.mqtt.baseUrl");

                instance = new MqttClient(MQTT_SERVER_ADDRES, MQTT_PUBLISHER_ID);
                connect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }


        }


        return instance;
    }

    public void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(10);

        if (!instance.isConnected()) {
            instance.connect(options);
        }
    }

    public void close() throws MqttException {
        instance.close();
    }

}