import { Component } from '@angular/core';
import { Subnet } from './subnet';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ipadressen';
  allSubnets: Subnet[] = [];
  adressTextField = ""
  numberSubnetsTextField = "";

  ip = "";
  cidr = "";


  public classReference = AppComponent;
  constructor() {
    console.log("AppComponent created");
    // let subnet: Subnet = {
    //   netId: "192.168.33.0",
    //   firstId: "192.168.33.1", 
    //   lastId: "192.168.33.62", 
    //   broadcastId: "192.168.33.63" 
    // };
    // this.allSubnets.push(subnet);
  }

  calculateSubnets(){
    console.log("add Subnet");
    this.validateInput();
    const oktets = this.getBinarIp(this.ip);
    console.log(oktets);
    const ip_back = this.convertBinarToIp(oktets);
    console.log(ip_back)
    this.createIpData(this.ip, Number(this.cidr), Number(this.numberSubnetsTextField))
  }

  validateInput(){
    if(this.adressTextField.includes("/")){
      let ip_split = this.adressTextField.split("/");
      this.ip = ip_split[0];
      this.cidr = ip_split[1];
      console.log(this.ip + "  " + this.cidr);
      if(this.ip.split(".").length == 4){
        let oktets = this.ip.split(".")
        console.log(oktets[0] + " " + oktets[1] + " " + oktets[2] + " " + oktets[3])
        console.log("Valid Input .... continue")
      } else {
        console.log("Invalid Ip input (x.x.x.x/y)")
      }
    } else {
      console.log("Invalid Ip input (x.x.x.x/y)");
    }
  }

  createIpData(ip: String, cidr: number, numberRequestedSubnet: number){
    this.allSubnets = [];
    let bitsForSubnet = this.getSubnet(numberRequestedSubnet);
    let clientsCount = 32 - cidr - bitsForSubnet;
    let basisIp = this.getBinarIp(ip).join("").substring(0, cidr);

    for(let i=0; i<numberRequestedSubnet; i++){
      let binaryString = this.dec2bin(i).padStart(bitsForSubnet, "0");
      console.log(basisIp + binaryString + "".padEnd(clientsCount, "0"))
      let subnet: Subnet = {
        netId:  this.convertStringBinarToIp(basisIp + binaryString + "".padEnd(clientsCount, "0")),
        firstId: this.convertStringBinarToIp(basisIp + binaryString + "".padEnd(clientsCount-1, "0")+"1"),
        lastId: this.convertStringBinarToIp(basisIp + binaryString + "".padEnd(clientsCount-1, "1")+"0"),
        broadcastId: this.convertStringBinarToIp(basisIp + binaryString + "".padEnd(clientsCount, "1")),
        numberClients: Math.round(Math.pow(2, clientsCount))-2
      };
      console.log(subnet)
      this.allSubnets.push(subnet);
    }
    console.log(this.allSubnets);

  }

  convertBinarToIp(binarIp: String[]): String {
    let result = binarIp.map(oktet => parseInt(String(oktet), 2));
    return result.join(".");
  }
  convertStringBinarToIp(binarIp: String): String {
    let oktets = []
    oktets.push(binarIp.substring(0, 8))
    oktets.push(binarIp.substring(8, 16))
    oktets.push(binarIp.substring(16, 24))
    oktets.push(binarIp.substring(24, 32))
    console.log(oktets)
    let result = oktets.map(oktet => parseInt(String(oktet), 2));
    return result.join(".");
  }

  getBinarIp(ip: String): String[] {
    let oktets = this.ip.split("\.");
    oktets = oktets.map(oktet => this.dec2bin(Number(oktet)).padStart(8, "0"));
    return oktets
  } 

  getSubnet(numberRequestedSubnet: number): number {
    return Math.ceil(Math.log2(numberRequestedSubnet));
  }

  dec2bin(dec: number) {
    return (dec >>> 0).toString(2);
  }

}
