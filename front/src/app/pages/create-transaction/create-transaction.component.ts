import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { map, Observable, startWith } from 'rxjs';
import IClient from 'src/app/interfaces/IClient';
import ITransaction from 'src/app/interfaces/ITransaction';
import ITransactionPayload from 'src/app/interfaces/ITransactionPayload';
import { AuthService } from 'src/app/service/auth.service';
import { ClientService } from 'src/app/service/client.service';
import { SkuService } from 'src/app/service/sku.service';
import { TransactionService } from 'src/app/service/transaction.service';

import ISku from '../../interfaces/ISku';

@Component({
    selector: 'app-create-transaction',
    templateUrl: './create-transaction.component.html',
    styleUrls: ['./create-transaction.component.css']
})
export class CreateTransactionComponent implements OnInit {
    skus: ISku[]
    clients: IClient[]
    filteredSkus: Observable<ISku[]>
    filteredClients: Observable<IClient[]>
    firstSku?: ISku
    isUp: boolean = false
    isDown: boolean = false
    InOut: boolean = true

    form: FormGroup

    constructor(
        private skuService: SkuService,
        private clientService: ClientService,
        private transactionService: TransactionService,
        private auth: AuthService,
        private notification: ToastrService,
        private formBuilder: FormBuilder) { }

    ngOnInit(): void {

        this.skuService.get().subscribe(
            data => {
                this.onGetSkus(data)
            }
        )
        this.clientService.get().subscribe(
            data => {
                this.onGetClients(data)
            }
        )

        this.configureForm()


        // this.filteredSkus.subscribe(ele => this.firstSku = ele[0])

    }

    onGetSkus(data: ISku[]) {
        this.skus = data
        this.filteredSkus = this.form.controls["sku"].valueChanges.pipe(
            startWith(''),
            map((value: any) => {
                const name = value = typeof value === 'string' ? value : value?.name;
                return name ? this._filterSkus(name as string) : this.skus.slice();
            }),
        );
    }

    private _filterSkus(name: string): ISku[] {
        const filterValue = name.toLowerCase();
        return this.skus.filter(sku => sku.name?.toLowerCase().includes(filterValue));
    }

    onGetClients(data: IClient[]) {
        this.clients = data
        this.filteredClients = this.form.controls["client"].valueChanges.pipe(
            startWith(''),
            map((value: any) => {
                const name = value = typeof value === 'string' ? value : value?.name;
                return name ? this._filterClients(name as string) : this.clients.slice();
            }),
        );
    }

    private _filterClients(name: string): IClient[] {
        const filterValue = name.toLowerCase();
        return this.clients.filter(client => client.name?.toLowerCase().includes(filterValue));
    }

    configureForm() {
        this.form = this.formBuilder.group({
            sku: [null, [Validators.required]],
            client: [null, [Validators.required]],
            quantity: [0, [Validators.required]],
            type: [""]
        })
    }


    toggleMenu(): void {
        this.InOut = !this.InOut;
    }

    inOutReturn(): string{
        if(this.InOut == true){
            return "IN"
        } else{
            return "OUT"
        }
    }


    skuFallback() { }

    clientFallback() { }

    displaySku(sku: ISku): string {
        return sku && sku.name ? sku.name : '';
    }

    displayClient(client: IClient): string {
        return client && client.name ? client.name : '';
    }

    createTransaction() {
        const branchId = this.auth.getUserBranchId()
        if (branchId != null) {
            const newTransaction: ITransactionPayload = {
                user: {
                    id: this.auth.getUserId() as unknown as number
                },
                type: this.inOutReturn(),
                client: {
                    id: this.form.controls["client"].value.id,
                },
                sku: {
                    id: this.form.controls["sku"].value.id,
                },
                quantity: this.form.controls["quantity"].value,
                warehouseSlot: {
                    warehouseSlotId: {
                        branch: {
                            id: this.auth.getUserBranchId()
                        }
                    }
                }
                // user: {
                //   id: this.auth.getUserId() as unknown as number
                // },
                // type: this.form.controls["type"].value,
                // client: this.form.controls["client"].value,
                // sku: this.form.controls["sku"].value,
                // quantity: this.form.controls["quantity"].value,
                // warehouseSlot: {
                //   warehouseSlotId: {
                //     branch: {
                //       id: this.auth.getUserBranchId()
                //     }
                //   }
                // }
            }
            console.log(newTransaction)
            this.transactionService.create(newTransaction).subscribe(
                (respose: ITransaction[]) => {
                    respose.map(transaction => {
                        console.log(transaction)
                        console.log(transaction.type)
                        let msg = ""
                        let title = ""
                        if (transaction.type === "IN") {
                          title = "In!"
                            msg = `Product: ${transaction.sku}</br>Quantity: ${transaction.quantity}</br>Slot: ${transaction.warehouseSlot}`
                        }
                        if (transaction.type === "OUT") {
                          title = "Out!"
                          msg = `Product: ${transaction.sku}</br>Quantity: ${transaction.quantity}</br>Slot: ${transaction.warehouseSlot}`
                        }
                        console.log(msg)
                        this.notification.success(
                            msg,
                            title,
                            {
                                // tapToDismiss: true,
                                disableTimeOut: true,
                                closeButton: true,
                                enableHtml: true
                            }
                        );
                        console.log(respose)
                    })
                },
                (error) => {
                    console.log(error)
                    this.notification.error(error.error.message, 'Error', {
                        tapToDismiss: true,
                        disableTimeOut: true,
                        closeButton: true,
                    });
                }
            );
        }
        else {
            this.notification.error("Only users with a Branch assigned, are allowed to create transactions!", 'Error', {
                progressBar: true,
            });
        }

    }
}
