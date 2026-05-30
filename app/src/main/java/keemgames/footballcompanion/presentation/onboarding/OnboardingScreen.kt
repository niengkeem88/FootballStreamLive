package keemgames.footballcompanion.presentation.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import keemgames.footballcompanion.presentation.components.ads.AdMobBannerAd
import keemgames.footballcompanion.presentation.theme.MidnightNavy
import keemgames.footballcompanion.presentation.theme.NeonPitchGreen
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: String
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "LIVE MATCHES",
        description = "Experience every goal and pulse-pounding moment with real-time updates from across the globe.",
        icon = "⚽"
    ),
    OnboardingPage(
        title = "INSTANT HIGHLIGHTS",
        description = "Missed the kick-off? Catch up with lightning-fast video recaps and match highlights.",
        icon = "📹"
    ),
    OnboardingPage(
        title = "PERSONALIZED ALERTS",
        description = "Never miss a beat. Get tailored notifications for your favorite teams and competitions.",
        icon = "🔔"
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // Gradient Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MidnightNavy,
                            Color(0xFF0A1F44).copy(alpha = 0.5f),
                            MidnightNavy
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingContent(page = onboardingPages[page])
            }

            // Bottom Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Pager Indicators
                Row(
                    Modifier
                        .height(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(onboardingPages.size) { iteration ->
                        val color = if (pagerState.currentPage == iteration) NeonPitchGreen else Color.Gray.copy(alpha = 0.5f)
                        val width by animateDpAsState(
                            targetValue = if (pagerState.currentPage == iteration) 32.dp else 8.dp,
                            label = "indicator_width"
                        )
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .width(width)
                                .height(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // CTA Button
                val isLastPage = pagerState.currentPage == onboardingPages.size - 1

                Button(
                    onClick = {
                        if (isLastPage) {
                            viewModel.completeOnboarding(onFinish)
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLastPage) NeonPitchGreen else Color.Transparent,
                        contentColor = if (isLastPage) MidnightNavy else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = if (isLastPage) null else ButtonDefaults.outlinedButtonBorder
                ) {
                    Text(
                        text = if (isLastPage) "ENTER ARENA" else "NEXT",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                // Banner ad below the button
                Spacer(modifier = Modifier.height(16.dp))
                AdMobBannerAd(
                    adUnitId = "ca-app-pub-3940256099942544/6300978111",
                    modifier = Modifier.fillMaxWidth()
                )

                // Spacer to avoid cutting off the ad at the bottom
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun OnboardingContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Placeholder (Large & Glowing)
        Surface(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            color = Color.White.copy(alpha = 0.05f),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.linearGradient(listOf(NeonPitchGreen, Color.Transparent))
            )
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = page.icon, fontSize = 80.sp)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = page.title,
            color = NeonPitchGreen,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}
