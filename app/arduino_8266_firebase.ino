#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include "DHT.h"

#define FIREBASE_HOST "iotserver-b77d8-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "uwDCNe6gBUNERYVVPvigNLfBhgK2TDl24sgeocKx"
#define WIFI_SSID "quyenHaHa"
#define WIFI_PASSWORD "0966733413"

int led2Status = 0;
int led4Status = 0;
int led5Status = 0;
int led4 = D6;
int led5 = D7;
int led2 = D1;
int DHTPin = D5;

DHT dht(DHTPin, DHT11);
float temperature;
float humidity;

void setup() {
  Serial.begin(115200);
  delay(1000);

  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led4, OUTPUT);
  pinMode(led5, OUTPUT);
  pinMode(DHTPin, INPUT);
  dht.begin();

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {
  digitalWrite(LED_BUILTIN, LOW);
  sendTempHumToFireBase();
  relay_2();
  relay_5();
  relay_4();
  delay(500);

}

void sendTempHumToFireBase(){
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  Firebase.setFloat("tempHum/1/temperature", temperature);
  Firebase.setFloat("tempHum/1/humidity", humidity);
  Serial.println(temperature);
  Serial.println(humidity);

}
void relay_2()
{
  led2Status = Firebase.getInt("relay/-MO2_csWRoSktYTmsZyt/status");
  if (led2Status == 1) {
    Serial.println("ON");
    digitalWrite(led2, HIGH);
  }
  else {
    Serial.println("OFF");
    digitalWrite(led2, LOW);
  }
}
void relay_5()
{
  led5Status = Firebase.getInt("relay/-MO6EAqU8RJx_YOQlmT_/status");
  if (led5Status == 1) {
    Serial.println("ON");
    digitalWrite(led5, HIGH);
  }
  else {
    Serial.println("OFF");
    digitalWrite(led5, LOW);
  }
}
void relay_4()
{
  led4Status = Firebase.getInt("relay/-MO6E5OMApUVVx2y1596/status");
  if (led4Status == 1) {
    Serial.println("ON");
    digitalWrite(led4, HIGH);
  }
  else {
    Serial.println("OFF");
    digitalWrite(led4, LOW);
  }
}