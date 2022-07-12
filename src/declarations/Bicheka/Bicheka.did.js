export const idlFactory = ({ IDL }) => {
  const User = IDL.Record({
    'id' : IDL.Principal,
    'bichekaBalance' : IDL.Nat,
    'usdBalance' : IDL.Nat,
  });
  return IDL.Service({
    'accountProperties' : IDL.Func([IDL.Principal], [User], ['query']),
    'addUser' : IDL.Func([IDL.Principal], [], ['oneway']),
    'changeBichekaToUSD' : IDL.Func([IDL.Nat], [IDL.Text], []),
    'changeUSDtoBicheka' : IDL.Func([IDL.Nat], [IDL.Text], []),
    'convertBichekaToUSD' : IDL.Func([IDL.Nat], [IDL.Nat], ['query']),
    'convertUSDtoBcicheka' : IDL.Func([IDL.Nat], [IDL.Nat], ['query']),
    'getBalance' : IDL.Func([IDL.Principal, IDL.Text], [IDL.Nat], ['query']),
    'getId' : IDL.Func([], [IDL.Principal], ['query']),
    'showSymbol' : IDL.Func([], [IDL.Text], ['query']),
    'transfer' : IDL.Func([IDL.Principal, IDL.Nat], [IDL.Text], []),
  });
};
export const init = ({ IDL }) => { return []; };
