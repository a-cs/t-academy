import { Component, OnInit } from '@angular/core';
import { ModalAddMeasuUnitComponent } from 'src/app/components/MeasurementUnitComponents/modal-add-measu-unit/modal-add-measu-unit.component';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { AuthService } from 'src/app/service/auth.service';
import { MeasurementUnitService } from 'src/app/service/measurement-unit.service';
import { buttonPermission } from 'src/app/utils/utils';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-measurement-unit',
    templateUrl: './measurement-unit.component.html',
    styleUrls: ['./measurement-unit.component.css'],
})
export class MeasurementUnitComponent implements OnInit {
    component = ModalAddMeasuUnitComponent;
    measurementUnits: IMeasurementUnit[];
    searchText: string;
    isLoading: boolean = false;
    isError: boolean = false;

    constructor(
        private measurementUnitService: MeasurementUnitService,
        public auth: AuthService,
        private notification: ToastrService
    ) { }

    btnPermission = buttonPermission;

    ngOnInit(): void {
        this.measurementUnitService.measurementUnitChanged.subscribe(() =>
            this.getData()
        );
        this.getData();
    }

    getData() {
        this.isError = false
        this.isLoading = true
        this.measurementUnitService.get().subscribe((data) => {
            this.measurementUnits = data;
            console.log(this.measurementUnits);
            this.isLoading = false
        }, error => {
            this.isLoading = false
            this.isError = true
            this.notification.error(error.error.message, 'Error: No serve response', {
              tapToDismiss: true,
              disableTimeOut: true,
              closeButton: true,
            });
          });
    }

    onSearchTextEntered(searchValue: string) {
        this.isError = false
        this.isLoading = true
        this.searchText = searchValue;
        this.measurementUnitService
            .getByLikeName(this.searchText)
            .subscribe((data) => {
                this.measurementUnits = data;
                console.log(this.measurementUnits);
                this.isLoading = false
            }, error => {
                this.isLoading = false
                this.isError = true
                this.notification.error(error.error.message, 'Error: No serve response', {
                  tapToDismiss: true,
                  disableTimeOut: true,
                  closeButton: true,
                });
              });
    }
}
