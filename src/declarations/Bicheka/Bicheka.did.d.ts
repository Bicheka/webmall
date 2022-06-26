import type { Principal } from '@dfinity/principal';
import type { ActorMethod } from '@dfinity/agent';

export interface _SERVICE {
  'balanceOf' : ActorMethod<[Principal, string], bigint>,
  'changeBichekaToUSD' : ActorMethod<[bigint], string>,
  'changeUSDtoBicheka' : ActorMethod<[bigint], string>,
  'convertBichekaToUSD' : ActorMethod<[bigint], bigint>,
  'convertUSDtoBcicheka' : ActorMethod<[bigint], bigint>,
  'getId' : ActorMethod<[], string>,
  'showSymbol' : ActorMethod<[], string>,
  'transfer' : ActorMethod<[Principal, bigint], string>,
}
