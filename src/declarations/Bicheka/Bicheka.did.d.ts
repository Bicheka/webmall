import type { Principal } from '@dfinity/principal';
import type { ActorMethod } from '@dfinity/agent';

export interface User {
  'id' : Principal,
  'bichekaBalance' : bigint,
  'usdBalance' : bigint,
}
export interface _SERVICE {
  'accountProperties' : ActorMethod<[Principal], User>,
  'addUser' : ActorMethod<[Principal], undefined>,
  'changeBichekaToUSD' : ActorMethod<[bigint], string>,
  'changeUSDtoBicheka' : ActorMethod<[bigint], string>,
  'convertBichekaToUSD' : ActorMethod<[bigint], bigint>,
  'convertUSDtoBcicheka' : ActorMethod<[bigint], bigint>,
  'getBalance' : ActorMethod<[Principal, string], bigint>,
  'getId' : ActorMethod<[], Principal>,
  'showSymbol' : ActorMethod<[], string>,
  'transfer' : ActorMethod<[Principal, bigint], string>,
}
