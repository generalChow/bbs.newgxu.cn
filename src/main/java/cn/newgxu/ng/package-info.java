/*
 * Copyright im.longkai@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 这里重新定义了一个命名空间，ng，代表的是“下一代”，类似于和以前不一样的东西(主要是面向移动终端）。
 * 注意，此包尽量不要和原来的耦合上，原来的结构怎么样就怎么样，不要去打破。
 * 原来的包原则上不要引入这个包的类，不要对这个类产生依赖）。
 * 
 * 这个包下是新的东西，搭配的是注解驱动的spring的inject和transactional等注解，以及jpa的一些工具。
 * 这里自己实现了一个注解驱动的小型mvc模式，定义在/ng/下的所有url命名空间。
 * 该模式有点象springmvc，基本上，参数绑定，异常处理，注入模型，返回视图都包含了。拦截器暂时先用javax的过滤器吧！
 * 
 * @longkai
 * @since 2013-02-27
 */
package cn.newgxu.ng;
