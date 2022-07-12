export const idlFactory = ({ IDL }) => {
  return IDL.Service({
    'balanceOf' : IDL.Func([IDL.Principal, IDL.Text], [IDL.Nat], ['query']),
    'changeBichekaToUSD' : IDL.Func([IDL.Nat], [IDL.Text], []),
    'changeUSDtoBicheka' : IDL.Func([IDL.Nat], [IDL.Text], []),
    'convertBichekaToUSD' : IDL.Func([IDL.Nat], [IDL.Nat], ['query']),
    'convertUSDtoBcicheka' : IDL.Func([IDL.Nat], [IDL.Nat], ['query']),
    'getId' : IDL.Func([], [IDL.Principal], ['query']),
    'showSymbol' : IDL.Func([], [IDL.Text], ['query']),
    'transfer' : IDL.Func([IDL.Principal, IDL.Nat], [IDL.Text], []),
  });
};
export const init = ({ IDL }) => { return []; };
