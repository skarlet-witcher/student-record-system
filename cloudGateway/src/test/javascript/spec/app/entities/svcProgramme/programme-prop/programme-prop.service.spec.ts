/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ProgrammePropService } from 'app/entities/svcProgramme/programme-prop/programme-prop.service';
import { IProgrammeProp, ProgrammeProp, ProgrammePropType } from 'app/shared/model/svcProgramme/programme-prop.model';

describe('Service Tests', () => {
    describe('ProgrammeProp Service', () => {
        let injector: TestBed;
        let service: ProgrammePropService;
        let httpMock: HttpTestingController;
        let elemDefault: IProgrammeProp;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProgrammePropService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new ProgrammeProp(0, 0, ProgrammePropType.GENERAL, 0, 0, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ProgrammeProp', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new ProgrammeProp(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProgrammeProp', async () => {
                const returnedFromService = Object.assign(
                    {
                        forEnrollYear: 1,
                        type: 'BBBBBB',
                        forYearNo: 1,
                        forSemesterNo: 1,
                        key: 'BBBBBB',
                        value: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ProgrammeProp', async () => {
                const returnedFromService = Object.assign(
                    {
                        forEnrollYear: 1,
                        type: 'BBBBBB',
                        forYearNo: 1,
                        forSemesterNo: 1,
                        key: 'BBBBBB',
                        value: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ProgrammeProp', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
