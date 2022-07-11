import Debug "mo:base/Debug";
import HashMap "mo:base/HashMap";
import Iter "mo:base/Iter";
import Principal "mo:base/Principal";
import Array "mo:base/Array";
import Text "mo:base/Text";
actor Bicheka {
  let owner : Principal = Principal.fromText("nixvz-oy2nx-gyo47-oyrwa-3bzsl-7sssq-7k6tk-wislm-4zwuf-u3dhq-mae");
  let totalSupply : Nat = 84000000000; //eighty four billion (25 times less that us dollars)
  let symbol : Text = "BCHKA";

  //array of tuples, that can contain multiples data types (Principal and Nat)
  private stable var usd: [(Principal, Nat)] = [];
  private stable var bicheka: [(Principal, Nat)] = [];
  private stable var userFriends: [(Principal, Text)] = [];
  //HashMap = dictionary in javascript
  //key data type of principal , and value data type of Nat


  public type FriendList = {
    name: Text;
    friendId: Principal;
  };

  //Create a user object data type
  public type User = {
    id : Principal;
    usdBalance : Nat;
    bichekaBalance : Nat;
  };

  //this is an instance of the object type user for the default case
  let dontExist: User = {
    id = Principal.fromText("2vxsx-fae");
    usdBalance = 0;
    bichekaBalance = 0;
  };
  
  //create a hashmap of with the principal ids and the user objects 
  private var userList: HashMap.HashMap<Principal, User> = HashMap.HashMap(1, Principal.equal, Principal.hash);
  private var usdBalance = HashMap.HashMap<Principal, Nat>(1, Principal.equal, Principal.hash);
  private var bichekaBalance = HashMap.HashMap<Principal, Nat>(1, Principal.equal, Principal.hash);
  private var usersFriendList = HashMap.HashMap<Principal, [FriendList]>(1, Principal.equal, Principal.hash);

  if(usdBalance.size() < 1){
    usdBalance.put(owner, 0);
  };

  if(bichekaBalance.size() < 1){
    bichekaBalance.put(owner, totalSupply);
  };

  //this funciton creates a User with properties such as an unique id, its balanca and friend list
  public func addUser (userId: Principal){
    let user: User = {
      id = userId; 
      usdBalance = 0; 
      bichekaBalance = 0;
    };
    //add the user object to the userList hashmap
    userList.put(userId, user);
  };

  //this function gets the properties of an accout 
  public query func accountProperties(userId: Principal): async User{
  let user: User = switch (userList.get(userId)){
      case null {dontExist};
      case (?result) {result};
    };
  };

  //set default value
  // let error: [FriendList] = [{name = "Error"; friendId = Principal.fromText("2vxsx-fae")}];
  // //adds a friend to the friend list
  // public func addFriend(userId: Principal, friendName: Text, id: Principal): async [FriendList]{
  //   var userFriends: [FriendList] = switch (usersFriendList.get(userId)){
  //     case null {error};
  //     case (?result) {result};
  //   };
  //   let newFriend: [FriendList] = [{name = friendName; friendId = id}];
    
  //   newList: [FriendList] = Array.append(userFriends, newFriend);

  // };

  

  //CHECK BALANCE
  public query func getBalance(id: Principal, currencyName: Text) : async Nat {
    // Debug.print(debug_show(msg.caller));
    if(currencyName == "USD"){
       let balance : Nat = switch (usdBalance.get(id)){
        case null 0;
        case (?result) result;
       };
    }
    else if(currencyName == "Bicheka"){
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
    
    let fromBalance = await getBalance(msg.caller, "bichekaBalance");
    if (fromBalance >= amount){
      let newFromBalance: Nat = fromBalance - amount;
      bichekaBalance.put(msg.caller, newFromBalance);
      let toBalance = await getBalance(to, "bichekaBalance");
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

    let fromBalance = await getBalance(msg.caller, "usdBalance");

    if (fromBalance >= amount){

      let newFromBalance: Nat = fromBalance - amount;
      usdBalance.put(msg.caller, newFromBalance);
      let toBalance = await getBalance(msg.caller, "bichekaBalance");
      let newToBalance = toBalance + (await convertUSDtoBcicheka(amount));
      bichekaBalance.put(msg.caller, newToBalance);

      return "Success";
    }
    else{
      return "insufficient funds";
    }
  };

    //changes the balance in Bicheka tokens to US Dollars
   public shared(msg) func changeBichekaToUSD(amount: Nat) : async Text{

    let fromBalance = await getBalance(msg.caller, "bichekaBalance");

    if (fromBalance >= amount){

      let newFromBalance: Nat = fromBalance - amount;
      bichekaBalance.put(msg.caller, newFromBalance);
      let toBalance = await getBalance(msg.caller, "usdBalance");
      let newToBalance = toBalance + (await convertBichekaToUSD(amount));
      usdBalance.put(msg.caller, newToBalance);

      return "Success";
    }
    else{
      return "insufficient funds";
    }
  };

  //gets the if of whoever calls the function
  public shared query({caller}) func getId() : async Principal{
    return caller;
  };

  //makes the calculation of how changing usd to Bicheka
  public query func convertUSDtoBcicheka(amount: Nat): async Nat{
    return amount / 25;
  };

  //calculates the change from Bicheka to usd
  public query func convertBichekaToUSD(amount: Nat): async Nat{
    return amount * 25;
  };

  //this will happen every time before an upgrade (in order to save the hash map data)
  // system func preupgrade(){
  //   //this line converts the balances hash map into an array
  //   usd := Iter.toArray(usdBalance.entries()); 
  //   bicheka := Iter.toArray(usdBalance.entries()); 
  // };

  // // //after the update transfer the array again to the hash map
  // system func postupgrade(){

  //   usdBalance := HashMap.fromIter<Principal, Nat>(usd.vals(), 1, Principal.equal, Principal.hash);
  //   bichekaBalance := HashMap.fromIter<Principal, Nat>(bicheka.vals(), 1, Principal.equal, Principal.hash);

  //   if((usdBalance.size() < 1) and (bichekaBalance.size() < 1)){
  //     usdBalance.put(owner, 0);
  //     bichekaBalance.put(owner, totalSupply);
  //   };
  // };

}