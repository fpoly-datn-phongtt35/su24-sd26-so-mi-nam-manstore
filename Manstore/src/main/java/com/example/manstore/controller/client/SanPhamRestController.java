package com.example.manstore.controller.client;


import com.example.manstore.CustomModel.FilterRequest;
import com.example.manstore.entity.SanPham;
import com.example.manstore.service.Impl.ChiTietSanPhamImpl;
import com.example.manstore.service.Impl.SanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"api/client/product",
        "/api/product"})
public class SanPhamRestController {

    @Autowired
    SanPhamServiceImpl service;

    @Autowired
    ChiTietSanPhamImpl chiTietService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private ResponseEntity<?> getALL() {
        return new ResponseEntity<>(service.getAllSanPham(), HttpStatus.OK);
    }

    @RequestMapping(value = "/priceMax", method = RequestMethod.GET)
    private ResponseEntity<?> PriceMax() {
        return new ResponseEntity<>(service.priceMax(), HttpStatus.OK);
    }


    @RequestMapping(value = "/page/{pageNumber}/{priceStart}/{priceEnd}", method = RequestMethod.POST)
    private ResponseEntity<?> paginationAndFilter(@PathVariable("pageNumber") String pageNumber,
                                                  @PathVariable("priceStart") String priceStart,
                                                  @PathVariable("priceEnd") String priceEnd,
                                                  @RequestBody FilterRequest filterRequest,
                                                  @RequestParam("sort") int sort,
                                                  @RequestParam(value = "trademark", required = false) String trademark,
                                                  @RequestParam(value = "category", required = false) String category) {
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber), 8, Sort.by("ngayTao").descending());
        Page<SanPham> page = null;

        if (sort == 1) {
            Sort sortCriteria = Sort.by(
                    Sort.Order.asc("ten"),
                    Sort.Order.desc("ngayTao")
            );
            pageable = PageRequest.of(Integer.parseInt(pageNumber), 8, sortCriteria);
            System.out.println("sort A-Z");
        } else if (sort == 2) {
            Sort sortCriteria = Sort.by(
                    Sort.Order.desc("ten"),
                    Sort.Order.desc("ngayTao")
            );
            pageable = PageRequest.of(Integer.parseInt(pageNumber), 8, sortCriteria);
            System.out.println("sort Z-A");
        } else if (sort == 3) {
            Sort sortCriteria = Sort.by(
                    Sort.Order.asc("gia"),
                    Sort.Order.desc("ngayTao")

            );
            pageable = PageRequest.of(Integer.parseInt(pageNumber), 8, sortCriteria);
            System.out.println("sort price desc");
        } else if (sort == 4) {
            Sort sortCriteria = Sort.by(
                    Sort.Order.desc("gia"),
                    Sort.Order.desc("ngayTao")
            );
            pageable = PageRequest.of(Integer.parseInt(pageNumber), 8, sortCriteria);
            System.out.println("sort price asc");
        } else {
            pageable = PageRequest.of(Integer.parseInt(pageNumber), 8, Sort.by("ngayTao").descending());
            System.out.println("sort default");
        }

        //default

        if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax())
                && (filterRequest.getListColors() == null && filterRequest.getListSizes() == null)
                && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.pageClient(pageable);
            System.out.println("Case Default");
        }
        //color
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() == null
                && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.filterColor(pageable, filterRequest.getListColors());
        }

        //size
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.filterSize(pageable, filterRequest.getListSizes());
        }
        //Trademark
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax())
                && filterRequest.getListColors() == null
                && filterRequest.getListSizes() == null
                && (category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterTrademark(pageable, trademark);
        }

        //Category
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax())
                && filterRequest.getListColors() == null
                && filterRequest.getListSizes() == null
                && (!category.equalsIgnoreCase("-1"))
                && trademark.equalsIgnoreCase("-1")) {
            System.out.println("category filter " + category);
            page = service.filterCatergory(pageable, category);
        }

        //price
        else if ((Integer.parseInt(priceStart) != 0 && Integer.parseInt(priceEnd) != service.priceMax() || Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) != service.priceMax()
                || Integer.parseInt(priceStart) != 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() == null && filterRequest.getListSizes() == null
                && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.price(pageable, priceStart, priceEnd);
        }
        //color+size
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.filterColorAndSize(pageable, filterRequest.getListColors(), filterRequest.getListSizes());
        }
        //color+brand
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() == null
                && (category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterTrademarkAndColor(pageable, trademark, filterRequest.getListColors());
        }
        //color+category
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() == null
                && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.filterColorAndCategory(pageable, trademark, filterRequest.getListColors());
        }
        //size+trademark
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                && (category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterSizeAndTrademark(pageable, trademark, filterRequest.getListSizes());
        }
        //size+category
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.filterSizeAndCategory(pageable, trademark, filterRequest.getListSizes());
        }
        //Trademark+Category
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() == null && filterRequest.getListSizes() == null
                && (!category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterTrademarkAndCategory(pageable, category, trademark);
        }
        //price+....
        else if ((Integer.parseInt(priceStart) > 0 && Integer.parseInt(priceEnd) <= service.priceMax() || Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) <= service.priceMax()
                || Integer.parseInt(priceStart) > 0 && Integer.parseInt(priceEnd) == service.priceMax())) {

            //brand+price
            if (filterRequest.getListColors() == null && filterRequest.getListSizes() == null
                    && (category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
                page = service.filterTrademarkAndPrice(pageable, trademark, priceStart, priceEnd);
            }
            //color+price
            else if (filterRequest.getListColors() != null && filterRequest.getListSizes() == null
                    && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.priceAndFilterColor(pageable, priceStart, priceEnd, filterRequest.getListColors());
            }
            //price+size
            else if (filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                    && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.priceAndFilterSize(pageable, priceStart, priceEnd, filterRequest.getListSizes());
            }
            //price+category
            else if (filterRequest.getListColors() == null && filterRequest.getListSizes() == null
                    && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.filterPriceAndCategory(pageable, category, priceStart, priceEnd);
            }
            //color+category+size
            else if (filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                    && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.filterColorAndSizeAndCategory(pageable, category, filterRequest.getListColors(), filterRequest.getListSizes());
            }
            //price+color+size
            else if (filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                    && (category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.priceAndFilterColorAndSize(pageable, priceStart, priceEnd, filterRequest.getListColors(), filterRequest.getListSizes());
            }
            //color+price+category
            else if (filterRequest.getListColors() != null && filterRequest.getListSizes() == null
                    && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.filterColorAndPriceAndCategory(pageable, category, priceStart, priceEnd, filterRequest.getListColors());
            }
            //size+trademark+price
            else if (filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                    && (category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
                page = service.filterSizeAndPriceAndTrademark(pageable, trademark, priceStart, priceEnd, filterRequest.getListSizes());
            }
            //size+price+category
            else if (filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                    && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.filterSizeAndCategoryAndPrice(pageable, category, priceStart, priceEnd, filterRequest.getListSizes());
            }
            //trademark+price+category
            else if (filterRequest.getListColors() == null && filterRequest.getListSizes() == null
                    && (!category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
                page = service.filterTrademarkAndCategoryAndPrice(pageable, category, trademark, priceStart, priceEnd);
            }
            //color+size+trademark+price
            else if (filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                    && (category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
                page = service.filterColorAndSizeAndTrademarkAndPrice(pageable, trademark, priceStart, priceEnd, filterRequest.getListColors(), filterRequest.getListSizes());
            }
            //color+size+category+price
            else if (filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                    && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
                page = service.filterColorAndSizeAndCategoryAndPrice(pageable, trademark, priceStart, priceEnd, filterRequest.getListColors(), filterRequest.getListSizes());
            }
            //color+trademark+category+price
            else if (filterRequest.getListColors() != null && filterRequest.getListSizes() == null
                    && (!category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
                page = service.filterColorAndPriceAndCategoryAndTrademark(pageable, category, trademark, priceStart, priceEnd, filterRequest.getListColors());
            }
            //size+brand+sport+price
            else if (filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                    && (!category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
                page = service.filterSizeAndPriceAndCategoryAndTrademark(pageable, category, trademark, priceStart, priceEnd, filterRequest.getListSizes());
            }
            //all(color+size+trademark+category+price)
            else {
                page = service.filterAll1(pageable, category, trademark, priceStart, priceEnd, filterRequest.getListColors(), filterRequest.getListSizes());
            }
        }
        //color+size+trademar
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                && (category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterColorAndSizeAndTrademark(pageable, trademark, filterRequest.getListColors(), filterRequest.getListSizes());
        }
        //color+trademark+category
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() == null
                && (!category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterColorAndTrademarkAndCategory(pageable, category, trademark, filterRequest.getListColors());
        }
        //size+trademark+category
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() == null && filterRequest.getListSizes() != null
                && (!category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterSizeAndCategoryAndTrademark(pageable, trademark, category, filterRequest.getListSizes());
        }
        //color+size+category
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                && (!category.equalsIgnoreCase("-1") && trademark.equalsIgnoreCase("-1"))) {
            page = service.filterColorAndSizeAndCategory(pageable, category, filterRequest.getListColors(), filterRequest.getListSizes());
        }
        //color+size+sport+brand
        else if ((Integer.parseInt(priceStart) == 0 && Integer.parseInt(priceEnd) == service.priceMax()) && filterRequest.getListColors() != null && filterRequest.getListSizes() != null
                && (!category.equalsIgnoreCase("-1") && !trademark.equalsIgnoreCase("-1"))) {
            page = service.filterColorAndSizeAndTrademarkAndCategory(pageable, category, trademark, filterRequest.getListColors(), filterRequest.getListSizes());
        } else {
            page = service.pageClient(pageable);
            System.out.println("Error??");
        }

        return new ResponseEntity<>(page, HttpStatus.OK);

    }
}
