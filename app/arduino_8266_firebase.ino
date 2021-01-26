#include <FirebaseESP8266.h>
#include  <ESP8266WiFi.h>
#include "DHT.h"

#define ssid "Quyendz"  //WiFi SSID
#define password "lengocquyen"  //WiFi Password
#define FIREBASE_HOST "iotserver-b77d8-default-rtdb.firebaseio.com"    
#define FIREBASE_AUTH "uwDCNe6gBUNERYVVPvigNLfBhgK2TDl24sgeocKx"     

FirebaseData firebaseData;

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
  
 Serial.begin(9600);
   WiFi.begin (ssid, password);
   while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println(".");
  }
  Serial.println ("");
  Serial.println ("WiFi Connected!");
  Serial.println();
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH); 
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led4, OUTPUT);
  pinMode(led5, OUTPUT);
  pinMode(DHTPin, INPUT);
  dht.begin();
}

void loop() {
  relay_2();
  relay_4();
  relay_5();
  sendTempHumToFireBase();
 }

 void relay_2()
{
    if (Firebase.get(firebaseData,"/relay/-MO2_csWRoSktYTmsZyt/status")){
    led2Status = firebaseData.intData();
    if (led2Status == 1) {
      Serial.println("ON");
      digitalWrite(led2, HIGH);
    }
    else {
      Serial.println("OFF");
      digitalWrite(led2, LOW);
    }
  }
}
void relay_5()
{
  if (Firebase.get(firebaseData,"/relay/-MO6EAqU8RJx_YOQlmT_/status")){
    led5Status = firebaseData.intData();
    if (led5Status == 1) {
      Serial.println("ON");
      digitalWrite(led5, HIGH);
    }
    else {
      Serial.println("OFF");
      digitalWrite(led5, LOW);
    }
  }
}
void relay_4()
{
  if (Firebase.get(firebaseData,"/relay/-MO6E5OMApUVVx2y1596/status")){
    led4Status = firebaseData.intData();
    if (led4Status == 1) {
      Serial.println("ON");
      digitalWrite(led4, HIGH);
    }
    else {
      Serial.println("OFF");
      digitalWrite(led4, LOW);
    }
  }
}
void sendTempHumToFireBase(){
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  Firebase.setFloat(firebaseData,"/tempHum/1/humidity", humidity);
  Firebase.setFloat(firebaseData,"/tempHum/1/temperature", temperature);
  Serial.println(temperature);
  Serial.println(humidity);
  delay(1000);

}
