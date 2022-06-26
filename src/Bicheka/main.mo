import Debug "mo:base/Debug";
import HashMap "mo:base/HashMap";
import Iter "mo:base/Iter";
import Principal "mo:base/Principal";
import Text "mo:base/Text";
actor Bicheka {
  let owner : Principal = Principal.fromText("nixvz-oy2nx-gyo47-oyrwa-3bzsl-7sssq-7k6tk-wislm-4zwuf-u3dhq-mae");
  let totalSupply : Nat = 84000000000; //eighty four billion (25 times less that us dollars)
  let symbol : Text = "BCHKA";

  //array of tuples, that can contain multiples data types (Principal and Nat)
  private stable var usd: [(Principal, Nat)] = [];
  private stable var bicheka: [(Principal, Nat)] = [];

  //HashMap = dictionary in javascript
  //key data type of principal , and value data type of Nat
  private var usdBalance = HashMap.HashMap<Principal, Nat>(1, Principal.equal, Principal.hash);
  private var bichekaBalance = HashMap.HashMap<Principal, Nat>(1, Principal.equal, Principal.hash);


  if(usdBalance.size() < 1){
    usdBalance.put(owner, 0);
  };

  if(bichekaBalance.size() < 1){
    bichekaBalance.put(owner, totalSupply);
  };

  //CHECK BALANCE
  public query func balanceOf(id: Principal, currencyName: Text) : async Nat {
    // Debug.print(debug_show(msg.caller));
    if(currencyName == "usdBalance"){
       let balance : Nat = switch (usdBalance.get(id)){
        case null 0;
        case (?result) result;
       };
    }
    else if(currencyName == "bichekaBalance"){
      let balance : Nat = switch (bichekaBalance.get(id)){
        case null 0;
        case (?result) result;
       };
    }
    else {
      return 0;
    }
  };

  public query func showSymbol() : async Text {
    return symbol;
  };

  // public shared(msg) func payOut() : async Text{ 
  //   //Debug.print(debug_show(msg.caller));
  //   if(balances.get(msg.caller) == null){
  //     //shared (msg) takes gives back the principal id of the caller
  //     let amount = 10000;
  //     let result = await transfer(msg.caller, amount);
  //     return result;
  //   }
  //   else{
  //     return "Already Claimed";
  //   }
  // };

  //transfer to to another account
  public shared(msg) func transfer(to: Principal, amount: Nat) : async Text{
    
    let fromBalance = await balanceOf(msg.caller, "bichekaBalance");
    if (fromBalance >= amount){
      let newFromBalance: Nat = fromBalance - amount;
      bichekaBalance.put(msg.caller, newFromBalance);
      let toBalance = await balanceOf(to, "bichekaBalance");
      let newToBalance = toBalance + amount;
      bichekaBalance.put(to, newToBalance);

      return "Success";
    }
    else{
      return "insufficient funds";
    }
    
  };

  //change money from USD to Bicheka
  public shared(msg) func changeUSDtoBicheka(amount: Nat) : async Text{

    let fromBalance = await balanceOf(msg.caller, "usdBalance");

    if (fromBalance >= amount){

      let newFromBalance: Nat = fromBalance - amount;
      usdBalance.put(msg.caller, newFromBalance);
      let toBalance = await balanceOf(msg.caller, "bichekaBalance");
      let newToBalance = toBalance + (await convertUSDtoBcicheka(amount));
      bichekaBalance.put(msg.caller, newToBalance);

      return "Success";
    }
    else{
      return "insufficient funds";
    }
  };

   public shared(msg) func changeBichekaToUSD(amount: Nat) : async Text{

    let fromBalance = await balanceOf(msg.caller, "bichekaBalance");

    if (fromBalance >= amount){

      let newFromBalance: Nat = fromBalance - amount;
      bichekaBalance.put(msg.caller, newFromBalance);
      let toBalance = await balanceOf(msg.caller, "usdBalance");
      let newToBalance = toBalance + (await convertBichekaToUSD(amount));
      usdBalance.put(msg.caller, newToBalance);

      return "Success";
    }
    else{
      return "insufficient funds";
    }
  };

  public shared query({caller}) func getId() : async Text{
    return Principal.toText(caller);
  };

  public query func convertUSDtoBcicheka(amount: Nat): async Nat{
    return amount / 25;
  };

  public query func convertBichekaToUSD(amount: Nat): async Nat{
    return amount * 25;
  };

  //this will happen every time before an upgrade (in order to save the hash map data)
  system func preupgrade(){
    //this line converts the balances hash map into an array
    usd := Iter.toArray(usdBalance.entries()); 
    bicheka := Iter.toArray(usdBalance.entries()); 
  };

  // //after the update transfer the array again to the hash map
  system func postupgrade(){

    usdBalance := HashMap.fromIter<Principal, Nat>(usd.vals(), 1, Principal.equal, Principal.hash);
    bichekaBalance := HashMap.fromIter<Principal, Nat>(bicheka.vals(), 1, Principal.equal, Principal.hash);

    if((usdBalance.size() < 1) and (bichekaBalance.size() < 1)){
      usdBalance.put(owner, 0);
      bichekaBalance.put(owner, totalSupply);
    };
  };

}