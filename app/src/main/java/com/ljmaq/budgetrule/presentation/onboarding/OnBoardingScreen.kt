package com.ljmaq.budgetrule.presentation.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ljmaq.budgetrule.R
import com.ljmaq.budgetrule.util.Screen
import com.ljmaq.budgetrule.util.isDigitsOnly
import java.util.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 87.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "App icon",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = "Welcome to Budget Rule", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Automatic budget manager", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(32.dp))
        var onSecondPage by remember {
            mutableStateOf(false)
        }
        Text(
            text = if (!onSecondPage) "Step 1 of 2" else "Step 2 of 2",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val color by animateColorAsState(
            targetValue = if (onSecondPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceColorAtElevation(
                100.dp
            ), label = "Color"
        )

        Row {
            ProgressIndicator(true)
            Spacer(modifier = Modifier.width(8.dp))
            ProgressIndicator(color = color)
        }
        var currencyCode by remember {
            mutableStateOf("")
        }

        var value by remember {
            mutableStateOf("")
        }

        AnimatedContent(
            targetState = !onSecondPage,
            label = "Content",
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(top = 24.dp),
            transitionSpec = {
                if (onSecondPage) {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    ) togetherWith slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    ) togetherWith slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.End
                    )
                }
                    .using(
                        SizeTransform(clip = false)
                    )
            }
        ) {
            if (it) {
                OnBoardingContent(
                    title = "Get started by selecting your currency"
                ) {
                    val initialCurrency = listOf<Currency>(
                        Currency.getInstance("PHP"),
                        Currency.getInstance("USD"),
                        Currency.getInstance("EUR"),
                        Currency.getInstance("JPY"),
                        Currency.getInstance("KRW")
                    )

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 48.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (currency in initialCurrency) {
                            CurrencyCard(
                                onClick = {
                                    onSecondPage = true
                                    currencyCode = currency.currencyCode
                                },
                                symbol = currency.symbol,
                                currencyCode = currency.currencyCode,
                                currency = currency.displayName
                            )
                        }

                        ElevatedCard(
                            onClick = { navController.navigate(Screen.OtherScreen.route) }
                        ) {
                            Text(
                                text = "Other",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                OnBoardingContent(
                    title = "Set the initial balance of the savings you have."
                ) {
                    val focusRequester by remember {
                        mutableStateOf(FocusRequester())
                    }
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    OutlinedTextField(
                        value = value,
                        onValueChange = { newValue ->
                            value = if (newValue.isDigitsOnly()) newValue else value
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.balance),
                                contentDescription = "Balance icon"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        ),
                        trailingIcon = {
                            Text(text = currencyCode)
                        },
                        label = {
                            Text(text = "Initial balance")
                        }, modifier = Modifier.focusRequester(focusRequester = focusRequester)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(visible = onSecondPage) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = {
                    onSecondPage = false
                }) {
                    Text(text = "Back")
                }
                Button(onClick = {
                    viewModel.onEvent(
                        OnBoardingEvent.Initialize(
                            currencyCode = currencyCode,
                            initialBalance = value.toDouble()
                        )
                    )
                    navController.navigate(Screen.HomeScreen.route + "?fromOnBoarding=true")

                }) {
                    Text(text = "Finish")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

        /**
         * @param onClick The action to be performed when clicked.
         * @param symbol The symbol of the currency to be displayed.
         * @param currencyCode The code of the currency to be displayed.
         * @param currency The name of the currency to be displayed on the card.
         */
fun CurrencyCard(
    onClick: () -> Unit,
    symbol: String,
    currencyCode: String,
    currency: String
) {
    ElevatedCard(
        onClick = onClick
    ) {
        ListItem(headlineContent = { Text(text = currencyCode) }, trailingContent = {
            Text(text = symbol, style = MaterialTheme.typography.headlineSmall)
        }, supportingContent = {
            Text(text = currency)
        })
    }
}

@Composable
fun OnBoardingContent(
    title: String,
    content: @Composable () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = title, modifier = Modifier.padding(bottom = 24.dp))
        content()
    }
}

@Composable
        /**
         * @param enabled Whether the color of the indicator is enabled
         * */
fun ProgressIndicator(
    enabled: Boolean = false,
    color: Color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceColorAtElevation(
        100.dp
    )
) {
    Box(
        modifier = Modifier
            .background(color = color, shape = CircleShape)
            .size(4.dp)
    )
}