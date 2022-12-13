export function capitalize(word: string) {
    return word.charAt(0).toUpperCase() + word.slice(1);
  }

export const routePermission = {
  home: ["ROLE_ADMIN","ROLE_MANAGER", "ROLE_OPERATOR", "ROLE_CLIENT"],
  products: ["ROLE_ADMIN","ROLE_MANAGER", "ROLE_OPERATOR"],
  clients: ["ROLE_ADMIN", "ROLE_MANAGER"],
  clientInventory: ["ROLE_CLIENT"],
  measurementUnits: ["ROLE_ADMIN","ROLE_MANAGER","ROLE_OPERATOR"],
  categories: ["ROLE_ADMIN","ROLE_MANAGER", "ROLE_OPERATOR"],
  branches: ["ROLE_ADMIN","ROLE_MANAGER"],
  users: ["ROLE_ADMIN","ROLE_MANAGER"],
  transactionHistory: ["ROLE_OPERATOR", "ROLE_MANAGER", "ROLE_ADMIN"],
  createTransaction: ["ROLE_MANAGER","ROLE_OPERATOR"],
  warehouseSlot: ["ROLE_OPERATOR", "ROLE_MANAGER"]
}

export const buttonPermission ={
  addProduct: ["ROLE_ADMIN","ROLE_MANAGER"],
  updateProduct: ["ROLE_ADMIN","ROLE_MANAGER"],
  deleteProduct: ["ROLE_ADMIN","ROLE_MANAGER"],
  addCategory: ["ROLE_ADMIN","ROLE_MANAGER"],
  updateCategory: ["ROLE_ADMIN","ROLE_MANAGER"],
  deleteCategory: ["ROLE_ADMIN","ROLE_MANAGER"],
  addUnit: ["ROLE_ADMIN","ROLE_MANAGER"],
  updateUnit: ["ROLE_ADMIN","ROLE_MANAGER"],
  deleteUnit: ["ROLE_ADMIN","ROLE_MANAGER"],
  addBranch: ["ROLE_ADMIN"],
  updateBranch: ["ROLE_ADMIN"],
  deleteBranch: ["ROLE_ADMIN"],
  selectClient: ["ROLE_ADMIN", "ROLE_MANAGER"]
}
